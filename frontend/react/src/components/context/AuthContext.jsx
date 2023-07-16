import {
    createContext,
    useContext,
    useEffect,
    useState

} from "react";


import {login as performedLogin} from "../../services/client.js";
import jwt_decode from "jwt-decode";

const  AuthContext = createContext({});

const  AuthProvider = ({children}) => {
    const  [customer, setCustomer] = useState(null);

    const setCustomerFromToken = () => {
        let token = localStorage.getItem("access_token");
        if(token){
            token=jwt_decode(token);
            setCustomer({
                username: token.sub,
                roles: token.scopes
            })
        }
    }

    useEffect(()=>{
        setCustomerFromToken();
    },[])

    const login = async  (usernameAndPassword)  =>{
      return new Promise((resolve,reject)=>{
          performedLogin(usernameAndPassword).then(res =>{
              const jwtToken = res.headers["authorization"];
              //TODO: save the token
              //console.log("resultat res  "+JSON.stringify(res));
              console.log("jwtToken!! " , jwtToken);
              localStorage.setItem("access_token",jwtToken)
              const decodedToken=jwt_decode(jwtToken);
              setCustomer({
                  username: decodedToken.sub,
                  roles: decodedToken.scopes
              })
              resolve(res);
          }).catch(err => {
              reject(err);
          })
      })
    }
    const  logOut = () => {
        localStorage.removeItem("access_token")
        setCustomer(null)
    }

    const isCustomerAuthenticated = () => {
        const token = localStorage.getItem("access_token");
        if(!token){
            return false;
        }
        const {exp:expiration} = jwt_decode(token);
        if(Date.now() > expiration * 1000){
            logOut();
            return false;
        }
        return true;
    }
    return (

        <AuthContext.Provider
            value = {{
            customer,
            login ,
            logOut,
            isCustomerAuthenticated,
            setCustomerFromToken
            }}
        >
            {children}
        </AuthContext.Provider>
      )
}



export const  useAuth = () => useContext(AuthContext);

export default AuthProvider;