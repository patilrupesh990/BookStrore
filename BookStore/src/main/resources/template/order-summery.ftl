<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <meta charset="utf-8">
    <title></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <style>
    img{
  height: 100px;
  width:100px;
  margin-left: 20px;
}
.heading{
  width: 100%;
  margin-left: 0px;
  margin-right: 0px;
  margin-top: 30px;
}
.name{
  margin-top: 30px;
  margin-left: 50px;
}
.msg{
  margin-top: 30px;
  margin-left: 50px;

}
.msg-2{
  margin-top: 30px;
  margin-left: 50px;
  border: 1px solid #C0C0C0;
  padding: 20px;
  background-color: #DCDCDC;
}
.order-id{
  margin-top: 30px;
  margin-left: 50px;
}
    
    </style>
  </head>
  <body>
    <div class="container">
      <div class="heading">
        <img style="height: 100px;
  width:100px;
  margin-left: 20px;" src="https://images.vexels.com/media/users/3/145037/isolated/preview/b5456bdd96e3d28756e6d6f6b3d0eae6-man-reading-book-floor-silhouette-by-vexels.png">
      </div>
       <hr>
      <div class="name">
        <span style="font-size:20px;">Hey</span>
        <span style="font-family:bold;font-size:20px;">${name}</span>
      </div>
      <div class="msg">
        <span style="font-family:bold;font-size:30px; color:orange">&#10004;</span>
        <span style="font-family:bold;font-size:30px;  color:orange">Your Order Is Confirmed</span>
      </div>
      <div class="msg-2">
        <span style="font-size:20px;"><p>Thank You For Shopping With us Your items will be Packed Soon by Seller, your Order is  confirmed & your order will be delivered within 7 working days.<br>You Can visit Your <a href="http://localhost:4200/greeting">Account&billing page</a></p></span>
      </div>
      <hr>
      <div class="order-id">
        <div style="float: left;">
        <span style="font-size:20px;">Order Id:</span>
        <span style="font-size:20px; color:#4863A0 ">#6777CGF434D222</span>
      </div>
      <div style="float: right;">
        <div>
            <span style="font-size:20px;">Sub Total:${total}</span>
        </div>
        <div>
          <span style="font-size:20px;">+Tax:20</span>
        </div>
        <div style="font-size:20px; font-family:bold">
          Total:${total+20}
        </div>
      </div>
      </div>
      <div style="margin-top:100px;">
        <span style="font-size:30px;font-family:bold; color:grey">Contact Us</span>
      </div>
      <hr>
    </div>
    <div style="margin-top:20px;">
      <div >
        Email Us : patilrupesh990@gmail.com
      </div>
      <div>
        Call Us : +91-9558062808
      </div>
      <div>
        website : <a href="http://localhost:4200">www.bookstore.com</a>
      </div>
    </div>
  </body>
</html>
