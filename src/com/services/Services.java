package com.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.models.FeedbackModel;
import com.models.ModelConstants;
import com.models.ReportModel;
import com.models.UserModel;



@Path("/")
public class Services {

	@SuppressWarnings("unchecked")
	@POST
	@Path("/signup")
	@Produces(MediaType.TEXT_PLAIN)
	public String signUp(@FormParam("name") String name,
			@FormParam("email") String email, @FormParam("password") String password
			,@FormParam("type") String type ,@FormParam("phoneNumber") String phoneNumber ,
			@FormParam("verified") int verified ) throws SQLException {
		UserModel user = UserModel.addNewUser(name, email, password ,phoneNumber , verified , type );
		if(user != null) {
			JSONObject json = new JSONObject();
			json.put("id", user.getId());
			json.put("name", user.getName());
			json.put("email", user.getEmail());
			json.put("password", user.getPass());
			json.put("type" , user.getType());
			json.put("phoneNumber", user.getPhoneNumber());
			json.put("verified", user.getVerified());
			
			return json.toJSONString();			
		}else {
			JSONObject json = new JSONObject();
			json.put("operation", "failed");
			return json.toString();
		}
		//return null;
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/login")
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@FormParam("email") String email,
			@FormParam("password") String pass) {
		
		UserModel user = UserModel.login(email, pass);
		if(user != null) {
			JSONObject json = new JSONObject();
			json.put("id", user.getId());
			json.put("name", user.getName());
			json.put("email", user.getEmail());
			json.put("password", user.getPass());
			json.put("type" , user.getType());
			json.put("phoneNumber", user.getPhoneNumber());
			json.put("verified", user.getVerified());
			json.put("operation", "done");
			return json.toJSONString();			
		}
		JSONObject fail = new JSONObject();
		fail.put("operation", "failed");
		return fail.toString();
	}
	@SuppressWarnings("unchecked")
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		
		return "Test test";
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/addreport")
	@Produces(MediaType.TEXT_PLAIN)
	public String addReport(@FormParam("userid") int Id , @FormParam("description") String description
			, @FormParam("type") String type ,@FormParam("priority")int priority,
			@FormParam("imageurl") String url , @FormParam("lon") double longtuide
			,@FormParam("lat") double latitude , @FormParam("solved") int solved) throws SQLException {
		UserModel user = new UserModel();
		user.setId(Id);
		ReportModel report = new ReportModel();
		
		report = ReportModel.addReport(Id, description, type, url, priority, longtuide, latitude , solved);
		if(report!=null){
			JSONObject json = new JSONObject();
			json.put("user_id" , report.getUserId());
			json.put("description" , report.getDescription());
			json.put("report_id" , report.getReportId());
			json.put("type" , report.getType());
			json.put("imageUrl", report.getImageUrl());
			json.put("priority" , report.getPriority());
			json.put("lon" , report.getLongtuide());
			json.put("lat" , report.getLatitude());
			json.put("url" , report.getImageUrl());
			return json.toString();
		}
		else{JSONObject json = new JSONObject();
		json.put("operation" , "failed");
			
			return json.toString();
		}
		
	}
	@SuppressWarnings("unchecked")
	@GET
	@Path("/getallreports")
	@Produces(MediaType.TEXT_PLAIN)
	public String getAllReports() throws SQLException{
		ArrayList<ReportModel> data = new ArrayList<>();
		data = ReportModel.getAllReports();
		if(data!=null){
			JSONArray returnarray = new JSONArray();
			
		for(int i=0 ; i<data.size() ; i++){
			ReportModel report = new ReportModel();
			JSONObject json = new JSONObject();
			report = data.get(i);
			json.put("user_id" , report.getUserId());
			json.put("description" , report.getDescription());
			json.put("report_id" , report.getReportId());
			json.put("type" , report.getType());
			json.put("imageUrl", report.getImageUrl());
			json.put("priority" , report.getPriority());
			json.put("lon" , report.getLongtuide());
			json.put("lat" , report.getLatitude());
			returnarray.add(json);
		}
		return returnarray.toString();
		}
		else {
			JSONObject o = new JSONObject();
			o.put("operation" ,"failed");
			return o.toString();
		}
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/addfeedback")
	@Produces(MediaType.TEXT_PLAIN)
	public String addFeedback(@FormParam("userid") int Id , @FormParam("reportid") int reportId ,
			@FormParam("description") String description) throws SQLException{
		FeedbackModel feedback = FeedbackModel.addFeedback(reportId, description);
		if(feedback!= null){
			JSONObject data = new JSONObject();
			data.put("description", feedback.getDescription());
			data.put("feedbackid", feedback.getFeedbackId());
			data.put("reportid", feedback.getReportId());
			return data.toString();
			
		}
		else {
			JSONObject fail = new JSONObject();
			fail.put("operarion", "failed");
			return fail.toString();
		}
	}
}
	
	
	/*
	@SuppressWarnings("unchecked")
	@POST
	@Path("/updatePosition")
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePosition(@FormParam("id") String id,
			@FormParam("lat") String lat, @FormParam("long") String lon) {
		Boolean status = UserModel.updateUserPosition(Integer.parseInt(id), Double.parseDouble(lat), Double.parseDouble(lon));
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/follow")
	@Produces(MediaType.TEXT_PLAIN)
	public String followUser(@FormParam("srcid") String srcID,
							 @FormParam("dstid") String dstID) {
		boolean status = UserModel.addFollower(Integer.parseInt(srcID), Integer.parseInt(dstID));
		JSONObject json = new JSONObject();
		if(status) {
			NotificationFactory.createFollowNotification(Integer.parseInt(srcID), Integer.parseInt(dstID), new Date());  //Notify
			EventHistoryManager.saveFollowEvent(Integer.parseInt(srcID), Integer.parseInt(dstID), new Date()); //Log to history
		}
		json.put("status", status ? 1 : 0); //1 for successful execution, 0 otherwise.
		return json.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/followers")
	@Produces(MediaType.TEXT_PLAIN)
	public String getFollowers(@QueryParam("id") String id) {
		ArrayList<UserModel> followers = UserModel.getFollowers(Integer.parseInt(id), false);
		if(followers != null && !followers.isEmpty()) {
			JSONArray jsonArray = new JSONArray();
			JSONObject json;
			for(UserModel temp : followers) {
				json = new JSONObject();
				json.put("id", temp.getId());
				json.put("name", temp.getName());
				jsonArray.add(json);
			}
			return jsonArray.toString();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/following")
	@Produces(MediaType.TEXT_PLAIN)
	public String getFollowing(@QueryParam("id") String id) {
		ArrayList<UserModel> followers = UserModel.getFollowers(Integer.parseInt(id), true);
		if(followers != null && !followers.isEmpty()) {
			JSONArray jsonArray = new JSONArray();
			JSONObject json;
			for(UserModel temp : followers) {
				json = new JSONObject();
				json.put("id", temp.getId());
				json.put("name", temp.getName());
				jsonArray.add(json);
			}
			return jsonArray.toString();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/unfollow")
	@Produces(MediaType.TEXT_PLAIN)
	public String unfollowUser(@FormParam("srcid") String master , @FormParam("dstid")String slave) {
		boolean status = UserModel.unfollow(Integer.parseInt(master), Integer.parseInt(slave));
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0); //1 for successful execution, 0 otherwise.
		return json.toJSONString();
	}

	@SuppressWarnings("unchecked")
	@GET
	@Path("/lastposition")
	@Produces(MediaType.TEXT_PLAIN)
	public String getLastPosition(@QueryParam("id") String userID) {
		PlaceModel Place = UserModel.getLastPosition(Integer.parseInt(userID));
		JSONObject json = new JSONObject();
		json.put("lat", Place.getLatitude());
		json.put("long", Place.getLongitude());
		return json.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/post")
	@Produces(MediaType.TEXT_PLAIN)
	public String postCheckin(@FormParam("userid") String userID,
			@FormParam("placeid") String placeID, @FormParam("body") String postBody) {
		CheckinModel checkinPost = CheckinModel.addNewPost(postBody, new Date(),
				Integer.parseInt(userID), Integer.parseInt(placeID));
		if(checkinPost != null) {
			JSONObject json = new JSONObject();
			json.put("id", checkinPost.getId());
			json.put("body", checkinPost.getPostBody());
			json.put("date", checkinPost.getTimestamp().getTime());
			EventHistoryManager.savePostEvent(Integer.parseInt(userID), checkinPost.getId(), new Date());
			return json.toJSONString();
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
    @POST
    @Path("/addplace")
    @Produces(MediaType.TEXT_PLAIN)
    public String addPlace(@FormParam("name") String name, @FormParam("desc") String desc,
    			@FormParam("lat") String lat, @FormParam("lon") String lon) {
    	PlaceModel place = PlaceModel.addNewPlace(name, desc, Double.parseDouble(lat), Double.parseDouble(lon));
    	JSONObject json = new JSONObject();
    	json.put("name", place.getName());
    	json.put("desc", place.getDesc());
    	json.put("lat", place.getLatitude());
    	json.put("long", place.getLongitude());
    	return json.toJSONString();
    }
    
	@SuppressWarnings("unchecked")
    @POST
    @Path("/saveplace")
    @Produces(MediaType.TEXT_PLAIN)    
    public String savePlace(@FormParam("userid") String userID, @FormParam("placeid") String placeID) {
    	PlaceModel Place = UserModel.savePlace(Integer.parseInt(userID), Integer.parseInt(placeID));
    	JSONObject json = new JSONObject();
    	json.put("name", Place.getName());
    	json.put("desc", Place.getDesc());
    	json.put("lat", Place.getLatitude());
    	json.put("long", Place.getLongitude());
    	return json.toJSONString();
    }
    
	@SuppressWarnings("unchecked")
	@POST
	@Path("/search")
	@Produces(MediaType.TEXT_PLAIN)
	public String search(@FormParam("query") String query, @FormParam("filter") String filter) {
		int searchFilter = Integer.parseInt(filter);
		ArrayList<UserModel> userList = null;
		ArrayList<PlaceModel> placeList = null;
		JSONObject resultObject = new JSONObject();
		JSONArray searchArray;
		
		if(searchFilter == Searcher.NO_FILTER) {
			userList = Searcher.searchUser(query);
			placeList = Searcher.searchPlace(query);
		} else if (searchFilter == Searcher.FILTER_USER)
			userList = Searcher.searchUser(query);
		else
			placeList = Searcher.searchPlace(query);
		
		if(userList != null) {
			searchArray = new JSONArray();
			for(UserModel x : userList) {
				JSONObject tempObj = new JSONObject();
				tempObj.put("userid", x.getId());
				tempObj.put("username", x.getName());
				tempObj.put("email", x.getEmail());
				tempObj.put("userlat", x.getLat());
				tempObj.put("userlong", x.getLon());
				searchArray.add(tempObj);
			}
			resultObject.put("users", searchArray);
		}
		
		if(placeList != null) {
			searchArray = new JSONArray();
			for(PlaceModel x : placeList) {
				JSONObject tempObj = new JSONObject();
				tempObj.put("placeid", x.getID());
				tempObj.put("placename", x.getName());
				tempObj.put("descrition", x.getDesc());
				tempObj.put("placelong", x.getLongitude());
				tempObj.put("placelat", x.getLatitude());
				searchArray.add(tempObj);
			}
			resultObject.put("places", searchArray);
		}
		return resultObject.toJSONString();
	}
    
	@SuppressWarnings("unchecked")
    @POST
    @Path("/like")
    @Produces(MediaType.TEXT_PLAIN)    
    public String addlike(@FormParam("userid") String userID, @FormParam("postid") String postID) {
    	boolean check = UserModel.addLike(Integer.parseInt(userID), Integer.parseInt(postID));
    	JSONObject json = new JSONObject();
    	if(check) {
    		NotificationFactory.createLikeNotification(Integer.parseInt(userID), Integer.parseInt(postID), new Date());  //Notify
    		EventHistoryManager.saveLikeEvent(Integer.parseInt(userID), Integer.parseInt(postID), new Date());
    	}
    		
    	json.put("status", check ? 1 : 0);
    	return json.toJSONString();
    }
    
	@SuppressWarnings("unchecked")
    @POST
    @Path("/comment")
    @Produces(MediaType.TEXT_PLAIN)
    public String addComment(@FormParam("userid") String userID, @FormParam("postid") String postID,
    		@FormParam("body") String commentBody) {
    	CommentModel comment = CommentModel.addComment(commentBody, new Date(), Integer.parseInt(userID), Integer.parseInt(postID));
    	if(comment != null) {
    		NotificationFactory.createCommentNotification(Integer.parseInt(userID), Integer.parseInt(postID), new Date());  //Notify
    		EventHistoryManager.saveCommentEvent(Integer.parseInt(userID), Integer.parseInt(postID), new Date());
    		JSONObject json = new JSONObject();
    		json.put("id", comment.getID());
    		json.put("commentbody", comment.getcommentBody());
    		json.put("time", comment.gettimeStamp().getTime());
    		return json.toJSONString();
    	}
    	return null;
    }
	
	@SuppressWarnings("unchecked")
    @POST
    @Path("/notifications")
    @Produces(MediaType.TEXT_PLAIN)
	public String getNotifications(@FormParam("userid") String userID) {
		ArrayList<Notification> notificationList = NotificationFactory.getNotifications(Integer.parseInt(userID));
		JSONArray jsonArray = new JSONArray();
		JSONObject notiJsonObj;
		if(notificationList != null) {
			for(Notification x : notificationList) {
				notiJsonObj = new JSONObject();
				notiJsonObj.put("id", x.getId());
				notiJsonObj.put("description", x.getDescription());
				notiJsonObj.put("type", x.getType());
				notiJsonObj.put("date", x.getTimestamp().getTime());
				notiJsonObj.put("target", x.getObjectID());
				jsonArray.add(notiJsonObj);
			}
			return jsonArray.toJSONString();
		}
		return null;
	}
	
    @GET
    @Path("/showpost")
    @Produces(MediaType.TEXT_PLAIN)
	public String getPost(@QueryParam("id") String postID) {
		JSONObject json = getJsonPost(Integer.parseInt(postID));
		if(json != null)
			return json.toJSONString();
		return null;
	}
	
	@SuppressWarnings("unchecked")
    @GET
    @Path("/showhome")
    @Produces(MediaType.TEXT_PLAIN)
    public String showHome(@QueryParam("id") String UserID) {
    	int userID = Integer.parseInt(UserID);    	
    	ArrayList<UserModel> followings = UserModel.getFollowers(userID, true);
    	JSONArray arr = new JSONArray();
    	
    	for(int i=0; i < followings.size(); i++) {
    		ArrayList<CheckinModel> posts = CheckinModel.getUserPosts(followings.get(i).getId());
    		for(int j=0; j < posts.size(); j++) {
    			JSONObject temp = getJsonPost(posts.get(j).getId());
    			if(temp != null)
    				arr.add(temp);
    		}
    	}
    	return arr.toJSONString();
    }
	
	@SuppressWarnings("unchecked")
    @GET
    @Path("/history")
    @Produces(MediaType.TEXT_PLAIN)
	public String getHistory(@QueryParam("id") String userID) {
		ArrayList<Event> eventList = EventHistoryManager.getHistory(Integer.parseInt(userID));
		JSONArray jsonArray = new JSONArray();
		JSONObject historyJsonObj;
		if(eventList != null) {
			for(Event e : eventList) {
				 historyJsonObj = new JSONObject();
				 historyJsonObj.put("id", e.getId());
				 historyJsonObj.put("description", e.getDescription());
				 historyJsonObj.put("type", e.getType());
				 historyJsonObj.put("date", e.getTimestamp().getTime());
				 historyJsonObj.put("target", e.getObjectID());
				 jsonArray.add(historyJsonObj);
			}
			return jsonArray.toJSONString();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
    @GET
    @Path("/savedplaces")
    @Produces(MediaType.TEXT_PLAIN)
	public String getsavedPlaces(@QueryParam("id") String userID) {
		ArrayList<PlaceModel> placeList = PlaceModel.getSavedPlaces(Integer.parseInt(userID));
		JSONArray jsonArray = new JSONArray();
		if(placeList != null) {
			for(PlaceModel x : placeList) {
				JSONObject placeJsonObj = new JSONObject();
				placeJsonObj.put("placeid", x.getID());
				placeJsonObj.put("placename", x.getName());
				placeJsonObj.put("descrition", x.getDesc());
				placeJsonObj.put("placelong", x.getLongitude());
				placeJsonObj.put("placelat", x.getLatitude());
				jsonArray.add(placeJsonObj);
			}
			return jsonArray.toJSONString();
		}
		return null;
	}
	@SuppressWarnings("unchecked")
    @GET
    @Path("/ila")
    @Produces(MediaType.TEXT_PLAIN)
	public String getsavedPlaces() {
		String x = new String("Hello from ILA");
		JSONObject as = new JSONObject();
		as.put("add", x);
		return x.toString();
	}
	//Not a service
	@SuppressWarnings("unchecked")
	private JSONObject getJsonPost(int postID) {
		CheckinModel post = CheckinModel.getPost(postID);
		
		if(post != null) {
			JSONObject resultJson = new JSONObject();
			JSONObject tempJson = new JSONObject();
			JSONArray tempJsonArr = new JSONArray();
			
			resultJson.put("postid", post.getId());
			resultJson.put("postbody", post.getPostBody());
			resultJson.put("postdate", post.getTimestamp().getTime()); //Retrieve as (long)
			
			//Add the place relative to this check-in post
			PlaceModel placeModel = PlaceModel.getPlace(post.getPlaceID());
			if(placeModel != null) {
				tempJson.put("placeid", placeModel.getID());
				tempJson.put("placename", placeModel.getName());
				tempJson.put("descrition", placeModel.getDesc());
				tempJson.put("long", placeModel.getLongitude());
				tempJson.put("lat", placeModel.getLatitude());
				
				resultJson.put("place", tempJson); //Retrieve as (JSONObject) (i.e JSONObject J = (JSONObject)e.get("place")) 
			}
			
			//Add IDs of users who like this post
			ArrayList<Integer> likeList = CheckinModel.getPostLikes(postID);
			if(likeList != null) {
				resultJson.put("likes", likeList); //Retrieve as (ArrayList) (i.e JsonOb = (ArrayList<Integer>)e.get("likes"))
			}
			
			//Add comments on this post & users made these comments
			ArrayList<CommentModel> commentList = CheckinModel.getPostComments(postID);
			if(commentList != null) {
				for(CommentModel cm : commentList) {
					tempJson = new JSONObject();
					tempJson.put("commentid", cm.getID()); //Retrieve as (int)
					tempJson.put("commentbody", cm.getcommentBody()); //Retrieve as (String) object
					
					//Note: I won't convert Date object to its string form as it causes violation to JSON standards
					//so it's converted to long, parse the long and create a new Date object using that long (i.e new Date(long_value))
					tempJson.put("commentdate", cm.gettimeStamp().getTime()); //Retrieve as (Date) object
					
					JSONObject tempJsonUser = new JSONObject();
					UserModel userModel = UserModel.getUser(cm.getID(), ModelConstants.COMMENT_MODEL);
					if(userModel != null) {
						tempJsonUser.put("id", userModel.getId());
						tempJsonUser.put("name", userModel.getName());
						tempJsonUser.put("email", userModel.getEmail());						
					}
					tempJson.put("owner", tempJsonUser); //Retrieve as (JSONObject)
					tempJsonArr.add(tempJson);
				}
				resultJson.put("comments", tempJsonArr); //Retrieve as (JSONArray)
			}
			return resultJson;
		}
		return null;
	}*/

