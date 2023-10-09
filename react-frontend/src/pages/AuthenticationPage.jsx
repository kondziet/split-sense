import AuthenticationForm from "../components/AuthenticationForm";

const AuthenticationPage = () => {
  return (
    <div className="flex flex-col justify-center min-h-full bg-gray-100">
      <div className="mx-auto w-full sm:max-w-md md:max-w-lg">
        <AuthenticationForm />
      </div>
    </div>
  );
};

export default AuthenticationPage;