import publicClientRequest, {
  privateClientRequest,
} from "../api/ClientRequest";
import useAuthenticationContext from "./useAuthenticationContext";

const useRefreshToken = () => {
  const { refreshToken, login } = useAuthenticationContext();

  const retrieveNewAccessToken = async () => {
    const response = await privateClientRequest.post(
      "/api/authentication/refresh-token",
      null,
      {
        headers: {
          Authorization: `Bearer ${refreshToken}`,
        },
      }
    );

    login(response.data.accessToken, response.data.refreshToken);

    return response.data.accessToken;
  };
  return retrieveNewAccessToken;
};

export default useRefreshToken;
