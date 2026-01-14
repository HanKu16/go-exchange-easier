import { v4 as uuidv4 } from "uuid";

export const getDeviceId = (): string => {
  const key = "deviceId";
  const deviceId = localStorage.getItem(key);
  if (deviceId === null) {
    const deviceId: string = uuidv4();
    localStorage.setItem(key, deviceId);
    return deviceId;
  } else {
    return deviceId;
  }
};
