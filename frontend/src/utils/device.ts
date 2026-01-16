import { v4 as uuidv4 } from "uuid";
import { UAParser } from "ua-parser-js";

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

export const getReadableDeviceName = () => {
  const parser = new UAParser();
  const result = parser.getResult();

  const browser = result.browser.name || "Unknown Browser";
  const os = result.os.name || "Unknown OS";
  const deviceModel = result.device.model;
  const deviceVendor = result.device.vendor;

  if (deviceModel && deviceVendor) {
    return `${deviceVendor} ${deviceModel} (${os})`;
  }
  return `${browser} on ${os}`;
};
