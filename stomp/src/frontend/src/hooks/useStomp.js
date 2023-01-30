import { Client } from "@stomp/stompjs";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import SockJS from "sockjs-client";
import { addChatMessage, addNotice } from "../redux/modules/stomp";

const useStomp = (client, destination) => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const token = useSelector((state) => state.auth.token);
  const headers = { Authorization: `Bearer ${token}` };
  const connect = () => {
    client.current = new Client({
      brokerURL: "ws://localhost:8080/ws",
      // webSocketFactory: () =>
      //   new SockJS(
      //     "http://localhost:8080/ws",
      //     {},
      //     { transports: ["xhr-polling"] }
      //   ),
      connectHeaders: { Authorization: `Bearer ${token}` },
      reconnectDelay: 200000,
      heartbeatIncoming: 16000,
      heartbeatOutgoing: 16000,
      onConnect: () => {
        console.log("STOMP 연결완료");
        if (destination !== undefined && destination !== "") {
          client.current?.subscribe(
            destination,
            (body) => {
              const json_body = JSON.parse(body.body);
              dispatch(addNotice(json_body));
            },
            headers
          );
          client.current?.subscribe(
            "/sub/private",
            (body) => {
              const json_body = JSON.parse(body.body);
              dispatch(addChatMessage(json_body));
            },
            headers
          );
        }
      },
      onStompError: (err) => {
        alert(err.headers.message);
        navigate("/login");
        console.log(err);
      },
      debug: (msg) => {
        console.log(msg);
      },
    });
    client.current?.activate();
  };

  const disconnect = () => {
    client.current?.deactivate();
  };

  return [connect, disconnect];
};

export default useStomp;
