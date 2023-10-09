import publicClientRequest, {
  privateClientRequest,
} from "../api/ClientRequest";
import useAuthenticationContext from "./useAuthenticationContext";

const useRefreshToken = () => {
  const { setAuthentication } = useAuthenticationContext();
  const refreshToken = localStorage.getItem("refreshToken");

  const refresh = async () => {
    const response = await privateClientRequest.post(
      "/api/authentication/refresh-token",
      null,
      {
        headers: {
          Authorization: `Bearer ${refreshToken}`,
        },
      }
    );
    setAuthentication((previous) => {
      console.log(JSON.stringify(previous));
      console.log(response.data.accessToken);
      return {
        ...previous,
        accessToken: response.data.accessToken,
        refreshToken: response.data.refreshToken,
      };
    });
    return response.data.accessToken;
  };
  return refresh;
};

export default useRefreshToken;
