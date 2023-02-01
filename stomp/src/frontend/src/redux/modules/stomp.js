import { createSlice } from "@reduxjs/toolkit";

const stompSlice = createSlice({
  name: "stomp",
  initialState: {
    client: "",
    subscriptions: [],
    messages: [],
    privateMessages: [],
    connectingUsers: "",
  },
  reducers: {
    saveStomp: (state, action) => {
      state.client = action.payload;
    },
    addSubscription: (state, action) => {
      state.subscriptions = [...state.subscriptions, action.payload];
    },
    removeSubscription: (state, action) => {
      state.subscriptions = state.subscriptions.filter(
        (item) => item.roomId !== action.payload.roomId
      );
      state.messages = state.messages.filter(
        (item) => item.roomId !== action.payload.roomId
      );
    },
    addChatMessage: (state, action) => {
      action.payload.isRead = state.subscriptions.find(
        (item) => item.roomId === action.payload.roomId
      ).isEnter;
      state.messages = [...state.messages, action.payload];
      if (!action.payload.isRead) {
        state.subscriptions = state.subscriptions.map((item) =>
          item.roomId === action.payload.roomId
            ? { ...item, notRead: ++item.notRead }
            : { ...item }
        );
      }
    },
    setIsEnter: (state, action) => {
      state.subscriptions = state.subscriptions.map((item) =>
        item.roomId === action.payload.roomId
          ? { ...item, isEnter: action.payload.isEnter }
          : { ...item }
      );
      if (action.payload.isEnter) {
        state.messages = state.messages.map((item) =>
          item.roomId === action.payload.roomId
            ? { ...item, isRead: true }
            : { ...item }
        );
        state.subscriptions = state.subscriptions.map((item) =>
          item.roomId === action.payload.roomId
            ? { ...item, notRead: 0 }
            : { ...item }
        );
      }
    },
    addNotice: (state, action) => {
      state.connectingUsers = action.payload;
    },
    addPrivateMessages: (state, action) => {
      if (action.payload.length > 1) {
        state.privateMessages = [...state.privateMessages, ...action.payload];
      } else {
        state.privateMessages = [...state.privateMessages, action.payload];
      }
    },
    removePrivateMessages: (state, action) => {
      state.privateMessages = state.privateMessages.filter(
        (item) => item.roomId !== action.payload
      );
    },
  },
});

export const {
  saveStomp,
  addSubscription,
  removeSubscription,
  addChatMessage,
  setIsEnter,
  addNotice,
  addPrivateMessages,
  removePrivateMessages,
} = stompSlice.actions;
export default stompSlice;
