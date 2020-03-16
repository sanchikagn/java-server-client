# java-server-client
A Java project with a server and a client which utilizes Java Threads

1.knock-knock example

* simple socket server and client

https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html

https://docs.oracle.com/javase/tutorial/networking/sockets/examples/KnockKnockClient.java

https://docs.oracle.com/javase/tutorial/networking/sockets/examples/KnockKnockProtocol.java

https://docs.oracle.com/javase/tutorial/networking/sockets/examples/KnockKnockServer.java

2.restaurant example

* for explaining multiple threads

-------------------------------------------------------
###Chronicles

1. simple socket client and a server

multiple clients and a server 
1. main thread handles all the clients, so other threads have to wait
2. for each client, a thread is created which is expensive
3. there is a fixed number of RequestProcessor threads where they acquire a task/ client request from TaskManager's taskList
4. a third party API call to fetch product and inventory info for client requests
5. introduced executor service, assign a child thread to fetch product info while client thread fetches inventory info