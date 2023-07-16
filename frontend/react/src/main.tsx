import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.tsx'
import { ChakraProvider } from '@chakra-ui/react'
import './index.css'
import Login from "./components/login/Login.jsx"
import Signup from "./components/signup/Signup.jsx"
import AuthProvider from "./components/context/AuthContext.jsx"
import ProtectedRoute from "./components/shared/ProtectedRoute.jsx"
import { createStandaloneToast } from '@chakra-ui/react'
import { createBrowserRouter,RouterProvider } from "react-router-dom";

const  router = createBrowserRouter([
    {
        path: "/",
        element:  <Login/>
    },
    {
        path: "signup",
        element:  <Signup/>
    },
    {
        path:"dashboard",
        element: <ProtectedRoute><App/></ProtectedRoute>
    }
])
const { ToastContainer} = createStandaloneToast()


ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <React.StrictMode>
      <ChakraProvider>
          <AuthProvider>
              <RouterProvider router={router} />
          </AuthProvider>
          <ToastContainer/>
      </ChakraProvider>
  </React.StrictMode>,
)
