package com.assignment.alexs.twitterclient.model;
/**
 * Created by alexschwartzman on 1/7/18.
 */


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TWSearchResults {

	@SerializedName("statuses")
	private ArrayList<TWStatus> statuses;

	@SerializedName("search_metadata")
	private TWSearchMetadata metadata;


	public ArrayList<TWStatus> getStatuses() {
		return statuses;
	}

	public void setStatuses(ArrayList<TWStatus> statuses) {
		this.statuses = statuses;
	}

	public TWSearchMetadata getMetadata() {
		return metadata;
	}

	public void setMetadata(TWSearchMetadata metadata) {
		this.metadata = metadata;
	}
}
