import { configureStore } from "@reduxjs/toolkit";
import authSlice from "./modules/auth";
import stompSlice from "./modules/stomp";

const store = configureStore({
  reducer: {
    auth: authSlice.reducer,
    stomp: stompSlice.reducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({ serializableCheck: false }),
  devTools: process.env.NODE_ENV !== "production",
});

export default store;
