package com.absolutegalaber.buildz.service

import com.absolutegalaber.buildz.domain.Server
import com.absolutegalaber.buildz.domain.exception.DataNotFoundException
import com.absolutegalaber.buildz.domain.exception.InvalidRequestException
import com.absolutegalaber.buildz.events.ReserveServerEvent
import com.absolutegalaber.buildz.repository.ServerRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import static com.absolutegalaber.buildz.repository.QuerySpecs.serverWithName

/**
 * A Service which handles all business logic related to Servers.
 */
@Service
@Transactional
class ServerService {
    private final ServerRepository serverRepository

    ServerService(ServerRepository serverRepository) {
        this.serverRepository = serverRepository
    }

    /**
     * Finds a Server in the database via its name.
     *
     * @param   name the name of the Server which should be fetched from the DB
     * @return  A Server with the provided name
     */
    Optional<Server> byName(String name) {
        serverRepository.findOne(serverWithName(name))
    }

    /**
     * Checks to see if a with the provided name exists in the DB.
     *
     * If it does, return it. If it does not, the Server is saved and that newly create Server is returned.
     *
     * @param serverName                the name of the Server which should be created or returned
     * @return                          the Server with the provided name or a newly created Server with the provided
     *                                  name
     * @throws InvalidRequestException  when the serverName is invalid
     */
    Server trackServer(String serverName) throws InvalidRequestException {
        if (!serverName?.trim()) {
            throw new InvalidRequestException("No Server Name provided")
        }

        Server server = serverRepository.findOne(serverWithName(serverName)).orElse(
                new Server(
                       name: serverName
                )
        )

        if (server.getId() <= 0L) {
            serverRepository.save(server)
        }

        return server
    }

    /**
     * Fetch all the name of every Server saved in the Buildz system.
     *
     * @return  a list of all server
     */
    List<Server> allServers() {
        serverRepository.findAll(Sort.by('name')).collect({it})
    }

    Server.Reservation reserveServerByName(
            String serverName,
            ReserveServerEvent event
    ) throws DataNotFoundException, InvalidRequestException {
        if (!serverName || !serverName.trim()) {
            throw new InvalidRequestException("No Server Name provided")
        }

        Server server = serverRepository.findOne(serverWithName(serverName))
                .orElseThrow(
                        {-> new DataNotFoundException("Server with name ${serverName} has not been registered")
                })

        if (!event.getReservedBy() || !event.getReservedBy().trim()) {
            throw new InvalidRequestException("Reservation event does not include who is reserving the server")
        }
        server.setReservation(new Server.Reservation(by: event.getReservedBy(), note: event.getReservationNote()))

        serverRepository.save(server).reservation
    }

    Server.Reservation reserveServer(Server server, ReserveServerEvent event) throws InvalidRequestException {
        // The validation of reservation by value is done outside of this method
        server.setReservation(new Server.Reservation(by: event.getReservedBy(), note: event.getReservationNote()))

        serverRepository.save(server).reservation
    }

    void releaseServerByName(String name) throws InvalidRequestException, DataNotFoundException {
        if (!name || !name.trim()) {
            throw new InvalidRequestException("No Server name provided")
        }

        Server server = serverRepository.findOne(serverWithName(name))
                .orElseThrow({
                    -> new DataNotFoundException("Server with name ${name} has not been registered")
                })
        server.setReservation(null)

        serverRepository.save(server)
    }
}
