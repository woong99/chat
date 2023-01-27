import { createSlice } from "@reduxjs/toolkit";

const stompSlice = createSlice({
  name: "stomp",
  initialState: { client: "", subscriptions: [], messages: [] },
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
      state.messages = [...state.messages, action.payload];
    },
    setIsEnter: (state, action) => {},
  },
});

export const {
  saveStomp,
  addSubscription,
  removeSubscription,
  addChatMessage,
  setIsEnter,
} = stompSlice.actions;
export default stompSlice;
