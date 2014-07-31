/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.pires.app.impl;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.corundumstudio.socketio.protocol.Packet;
import com.corundumstudio.socketio.protocol.PacketType;
import com.github.pires.app.MySocketIOApp;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Service
@Property(name = "service.exported.interfaces", value = "*")
public class MySocketIOAppImpl implements MySocketIOApp {

  private static final Logger log = LoggerFactory.getLogger(
      MySocketIOAppImpl.class);

  private static final String SERVER_ADDRESS = "0.0.0.0";
  private static final int SERVER_PORT = 8080;

  private SocketIOServer server;

  @Activate
  public void start() {
    Configuration config = new Configuration();
    config.setHostname(SERVER_ADDRESS);
    config.setPort(SERVER_PORT);

    server = new SocketIOServer(config);

    server.addConnectListener(new ConnectListener() {
      @Override
      public void onConnect(SocketIOClient client) {
        log.info("A new client has connected -> {}", client.getSessionId());
      }
    });

    server.addDisconnectListener(new DisconnectListener() {
      @Override
      public void onDisconnect(SocketIOClient client) {
        log.info("Client has disconnected -> {}", client.getSessionId());
      }
    });

    server.start();
  }

  @Deactivate
  public void stop() {
    server.stop();
  }

  @Override
  public void doSomething() {
    for (final SocketIOClient client : server.getAllClients()) {
      final Packet packet = new Packet(PacketType.MESSAGE);
      packet.setData("yeyyyyyyy!");
      client.send(packet);
    }
  }

}
