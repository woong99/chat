import { Client } from "@stomp/stompjs";
import axios from "axios";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import SockJS from "sockjs-client";
import {
  addChatMessage,
  addEnterUser,
  addNotice,
  addPrivateMessages,
  removeEnterUser,
  removePrivateMessages,
} from "../redux/modules/stomp";

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
            "/user/sub/private",
            (body) => {
              const json_body = JSON.parse(body.body);
              console.log(json_body);
              if (json_body.command === "MESSAGE") {
                dispatch(addPrivateMessages(json_body));
              } else if (json_body.command === "ENTER") {
                axios
                  .post("http://localhost:8080/private-room/message-list", {
                    roomId: json_body.roomId,
                  })
                  .then((res) => {
                    console.log(res);
                    dispatch(removePrivateMessages(json_body.roomId));
                    dispatch(addPrivateMessages(res.data));
                  });
                dispatch(
                  addEnterUser({
                    roomId: json_body.roomId,
                    user: json_body.writer,
                  })
                );
              } else if (json_body.command === "OUT") {
                dispatch(
                  removeEnterUser({
                    roomId: json_body.roomId,
                    user: json_body.writer,
                  })
                );
              }
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
