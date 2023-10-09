import { useEffect } from "react";
import useAuthenticationContext from "./useAuthenticationContext";
import { privateClientRequest } from "../api/ClientRequest";
import useRefreshToken from "./useRefreshToken";

const usePrivateClientRequest = () => {
  const { authentication } = useAuthenticationContext();
  const retrieveNewAccessToken = useRefreshToken();

  useEffect(() => {
    const requestIntercept = privateClientRequest.interceptors.request.use(
      (config) => {
        if (!config.headers.Authorization) {
          config.headers.Authorization = `Bearer ${authentication?.accessToken}`;
        }
        return config;
      },
      (error) => Promise.reject(error)
    );

    const responseIntercept = privateClientRequest.interceptors.response.use(
      (response) => response,
      async (error) => {
        const prevRequest = error?.config;
        if (error?.response?.status === 403 && !prevRequest?.sent) {
          prevRequest.sent = true;
          const newAccessToken = await retrieveNewAccessToken();
          prevRequest.headers["Authorization"] = `Bearer ${newAccessToken}`;
          return privateClientRequest(prevRequest);
        }
        return Promise.reject(error);
      }
    );

    const cleanup = () => {
      privateClientRequest.interceptors.request.eject(requestIntercept);
      privateClientRequest.interceptors.response.eject(responseIntercept);
    };

    return cleanup;
  }, [authentication, retrieveNewAccessToken]);

  return privateClientRequest;
};

export default usePrivateClientRequest;
