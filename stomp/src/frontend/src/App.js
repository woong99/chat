import { BrowserRouter, Route, Routes } from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import 'bootstrap/dist/css/bootstrap.min.css';
import SignUpPage from './pages/SignUpPage';
import MainPage from './pages/MainPage';
import ChatPage from './pages/ChatPage';
import { useSelector } from 'react-redux';
import PrivateRoute from './PrivateRoute';
import PublicChatRoomsPage from './pages/PublicChatRoomsPage';
import ConnectStompPage from './pages/ConnectStompPage';
import FriendsPage from './pages/FriendsPage';

function App() {
  const accessToken = useSelector((state) => state.auth.token);
  console.log(accessToken);
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/signup" element={<SignUpPage />} />
        <Route
          path="/public-chat-room"
          element={<PrivateRoute token={accessToken} component={<PublicChatRoomsPage />} />}
        />
        <Route
          path="/friends"
          element={<PrivateRoute token={accessToken} component={<FriendsPage />} />}
        />
        <Route
          path="/chat"
          element={<PrivateRoute token={accessToken} component={<ChatPage />} />}
        />
        <Route path="/" element={<PrivateRoute token={accessToken} component={<MainPage />} />} />
        <Route
          path="/connect-stomp"
          element={<PrivateRoute token={accessToken} component={<ConnectStompPage />} />}
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
