import useAuthenticationContext from "../hooks/useAuthenticationContext";
import usePrivateClientRequest from "../hooks/usePrivateClientRequest";
import useRefreshToken from "../hooks/useRefreshToken";

const Dashboard = () => {
  const refresh = useRefreshToken();
  const sendPrivateRequest = usePrivateClientRequest();
  const { logout } = useAuthenticationContext();

  return (
    <div>
      <h1>Hello zelo</h1>
      <div className="flex flex-col">
        <button onClick={() => refresh()}>click me</button>
        <button
          onClick={async () => {
            const response = await sendPrivateRequest.get("/api/hello");
            console.log(response);
          }}
        >
          protected route
        </button>
        <button onClick={() => logout()}>logout</button>
      </div>
    </div>
  );
};

export default Dashboard;
