<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%String user="";
if (request.getSession(false).getAttribute("username")!= null)
{user=request.getSession(false).getAttribute("username").toString();}%>
<style>
#masthead {
    background: url("images/pic4.png") no-repeat scroll center top / cover #223577;
    position: relative;
     color: #FFF; 
    z-index: 10;
    /* padding-top: 50px; */
}
.sitelogo {
	background-image: url("images/pic6.jpg");
    background-repeat: no-repeat;
    height: 58px;
    width: 114px;
    display: block;
    text-indent: -99999px;
    position: absolute;
    margin: 13px 0px 0px;
    padding: 0px;
    left: 0px;
}
.link{
color: orange;
}
</style>
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-62780809-1', 'auto');
  ga('send', 'pageview');

</script>
<script>
$('rules').click(function() {
	$("#myRules").modal();
});

</script>
<!-- <div class="masthead"> -->
<div align="center" class="navbar" id="masthead">
<div class="container-fluid">
 <ul class="nav navbar-nav">
        <li><a class="link" href="/t20/home">HOME</a></li>
       <!--  <li><a class="link" href="/t20/myScore">MY SCORE</a></li> -->
        <li><a class="link" href="/t20/allScores">ALL SCORE</a></li>
        <li><a class="link" href="/t20/graph1">LEAGUE 1</a></li>
        <li><a class="link" href="/t20/graph2">LEAGUE 2</a></li> 
        <li><a class="link" href="/t20/graph">OVERALL LEAGUE</a></li>
        <li><a class="link" data-toggle="modal" data-target="#myRules" id="rules" href="#" >RULES</a></li>
        
        <!-- <li><a class="link" href="/t20/teams">IPL TEAMS</a></li> -->
        <!-- <li><a class="link" href="/t20/league">LEAGUE</a></li>
        <li><a class="link" href="/t20/league/rules">LEAGUE RULES</a></li>-->
        </ul>
        
         <ul class="nav navbar-nav navbar-right">
               <li><a class="link" href="/t20/logout">LOGOUT - <%=user.toUpperCase()%></a></li>
            </ul>
     
</div>
</div>
<!-- Modal -->
  <div class="modal fade" id="myRules" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Rules</h4>
        </div>
        <div class="modal-body">
          <p>
          <div class="form-group">
 
 
  <div class="alert alert-info" role="alert">Points Rule !</div>
  <ul>
   <li>All matches from 1-56 are of 1 point.</li>
   <li>All matches from 57-59 are of 2 point.</li>
   <li>Final match is of 3 point.</li>
   <li>Pick one team as your favorite.</li>
   <li>Earn 2 bonus points if your team wins the IPL 2016.</li>  	
  </ul>
  <div class="alert alert-info" role="alert">Winner Declaration !</div>
  <ul>
   <li>3 winners will be declared through out the tournament.</li>
   <li>1 winner will be declared from League 1 (1-28).</li>
  <li>1 winner will be declared from League 2 (29-56).</li>
   <li>1 winner will be declared from overall tournament, League1 + League2 + Semi's + Finals. (Max points scorer)</li>
  	
  </ul>
 
</div>

          </p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>
<!-- <div align="right" style="padding-right: 5px;">
<script>app='www.cricwaves.com'; mo="h_p11"; nt="1";   wi ="n";  co ="7"; ad="1";</script>
<script type="text/javascript" src="http://www.cricwaves.com/cricket/widgets/script/scoreWidgets.js"></script>
<br/>
</div> -->