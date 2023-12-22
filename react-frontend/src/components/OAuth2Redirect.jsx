import React, { useEffect } from "react";

const OAuth2Redirect = () => {

    useEffect(() => {
        const accessToken = extractUrlParameter('token')
        console.log(accessToken);
      }, [])

    const extractUrlParameter = (parameterName) => {
        const urlParams = new URLSearchParams(window.location.search);
        return urlParams.get(parameterName);
    };

    return (
        <div>
            {/* Your component code here */}
        </div>
    );
};

export default OAuth2Redirect;
