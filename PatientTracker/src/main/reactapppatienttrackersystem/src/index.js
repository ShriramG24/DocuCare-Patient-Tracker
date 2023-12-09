import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';

//components
import Admin from "./layouts/Admin.js"
import RTL from "./layouts/RTL.js"
import {AccountBox} from './views/Login/accountBox';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <GlobalProvider>
  <Router history={hist}>
    
      <Route path="/user" component={Admin} />
      <Route path="/rtl" component={RTL} />
      <Route path="/" component={AccountBox} />
      {/* <Redirect from="/" to="/user/dashboard" /> */}
   
  </Router>
  </GlobalProvider>
  ,
  document.getElementById("root")
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
