<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Bosch Login Form</title>
        <style>
            html, body {
                height: 100%;
                width: 100%;
                padding: 0;
                margin: 0;
            }

            #full-screen-background-image {
                z-index: -999;
                min-height: 100%;
                width: 100%;
                height: auto;
                position: fixed;
                top: 0;
                left: 0;
            }

            .topRight {
                height: 30%;
                margin-left: 34%;
            }

            .bottomRight {
                height: 30%;
                margin-left: 34%;
            }

            .bottomLogo {
                float: right
            }

            .inputLogin {
                border-color: #565759;
                border-width: 0 0 2px;
                width: 304px;
                height: 33px;
            }

            .title {
                font-family: 'Open Sans', sans-serif;
                color: red;
                font-size: 30px
            }

            .signIn {
                background: white none repeat scroll 0 0;
                border: 1px solid #0572CE;
                font-family: 'Open Sans', sans-serif;
                font-size: 22px;
                height: 40px;
                width: 94px;
                color: #0572CE;
                font-size: 20px;
            }

            .left {
                height: 100%;
                float: left;
            }

            .centerRight {
                border-left: 7px solid #0572CE;
                height: 40%;
                margin-left: 33.5%;
                background: white none repeat scroll 0 0;
            }

            .centerInside {
                padding-left: 54px;
            }

            .body {
                background: url(resources/images/Bosch_Background.png);
            }
        </style>
        <script>
          if (self == top) {
              document.documentElement.style.visibility = 'visible';
          }
          else {
              top.location = self.location;
          }
        </script>
    </head>

    <body>
        <form method="POST" action="j_security_check" autocomplete="off">
            <div style="height: 100%;">
                <img alt="full screen background image" src="resources/images/Bosch_Background.jpg" id="full-screen-background-image"/>
                <div class="left"></div>
                <div class="topRight"></div>
                <div class="centerRight">
                    <div class="centerInside">
                        <br/>
                        <p class="title">Bosch Dashboard Login</p>
                        <input type="text" name="j_username" placeholder="Enter User Name" class="inputLogin"/><br/><br/>
                         
                        <input type="password" name="j_password" placeholder="Enter Password" class="inputLogin"/><br/><br/><br/>
                         
                        <input type="submit" name="submit" value="Sign In" class="signIn"/>
                    </div>
                </div>
                <div class="bottomRight">
                    <div class="bottomLogo">
                        <img src="resources/images/Bosch_logo_login.png" alt="Bosch Logo" height="200px" width="500px">
                            <?audit suppress oracle.ide.html.height-width?>
                        </img>                       
                    </div>
                </div>
            </div>
        </form>
    </body>
</html>