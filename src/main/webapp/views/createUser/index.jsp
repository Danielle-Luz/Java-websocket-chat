<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>Easychat | Create user</title>
    <link rel="stylesheet" href="./index.css" type="text/css" />
    <link rel="stylesheet" href="./../../styles/global.css" type="text/css" />
    <link rel="stylesheet" href="./../../styles/buttons.css" type="text/css" />
    <link rel="stylesheet" href="./../../styles/inputs.css" type="text/css" />
    <link rel="stylesheet" href="./../../styles/modal.css" type="text/css" />
    <link rel="stylesheet" href="./../../styles/forms.css" type="text/css" />
    <link rel="stylesheet" href="./../../styles/toasts.css" type="text/css" />
    <script src="./index.js" type="module" defer></script>
    <script src="./../../scripts/hideToast.js" type="text/javascript" defer></script>
  </head>
  <body class="content-container">
    <%
      String statusCode = request.getParameter("statusCode");
      if(statusCode != null) {
        String toastMessage = statusCode.equals("409") ? 
          "This user name was already taken" : 
          "An error occurred while trying to create your user";

        out.println("<article class=\"toast\"><img class=\"toast-icon\" src=\"https://img.icons8.com/material-outlined/24/FA5252/cancel--v1.png\" alt=\"Cancel icon\"/><h2 class=\"toast-title\">" + toastMessage +"</h2></article>");
      }
    %>
    <main class="form-external-container">
      <div class="form-container">
        <article class="text-container">
          <h2 class="form-title">New user</h2>
          <p class="form-description">
            Fill the fields below to start chatting!
          </p>
        </article>
        <form class="form" action="/createUser" method="POST">
          <fieldset class="input-container">
            <label class="input-label" for="username">Username</label>
            <input
              class="input-2"
              id="username"
              name="username"
              type="text"
              class=""
              placeholder="Type your username..."  
              autocomplete="off"
            />
          </fieldset>
          <fieldset class="input-container">
            <label class="input-label" for="password">Password</label>
            <input
              class="input-2"
              id="password"
              name="password"
              type="password"
              class=""
              placeholder="Type your password..."
              autocomplete="off"
            />
          </fieldset>
          <div class="button-group">
            <input
              class="primary-button register-button"
              type="submit"
              value="Register"
              disabled="true"
              autocomplete="off"
            />
            <a href="/views/login" class="terciary-button" type="button">Login</a>
          </div>
        </form>
      </div>
    </main>
  </body>
</html>
