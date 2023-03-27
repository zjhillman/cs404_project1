# Poll Server/Client System

#### For CS404

This system uses Java Socket to create a connection-oriented, concurrent client-server system.  
The system is a poll which the server collects votes from the clients and stores the results to be viewed by said clients after a ballot, or vote, has been cast.

## Compilation

In the terminal, please execute 
```
javac *.java
```

## Execution

### Server

To run the server you must use the following command
```
java VotingServer [PortNumber]
```
where PortNumber is the port you wish to use, default: 12320

### Client

To run the client you must use
```
java VotingClient [INetAddress] [PortNumber]
```
where INetAddress is the IP Address you wish to connect to, default: localhost  
and PortNumber is the port in which the host resides, default: 12320
