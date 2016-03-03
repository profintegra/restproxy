package com.constants;

public final class UrlConstants {
	
	public static final String HOST = "http://mgr.vizrt.it";
	
	public static final String AUTH_HEADER = "Coockie";
	
	public static final String LOGIN_URL = HOST + "/api/access/session";
	public static final String LOGIN_PARAMS = "User=%s&Passwd=%s";
	
	public static final String ITEM_META_SCHEMA_URL = HOST + "/api/metadata/form/hubitem/r1";
	public static final String SERIES_META_SCHEMA_URL = HOST + "/api/metadata/form/series/r1";
	public static final String PROGRAM_META_SCHEMA_URL = HOST + "/api/metadata/form/program/r1";
	public static final String ENTRIES_META_SCHEMA_URL = HOST + "/api/metadata/form/qcitem/r1";
	
	
	//metadata
	public static final String METADATA_URL = HOST + "/api/asset/item/%s/metadata";
	public static final String PACKAGES_META = HOST + "/api/asset/itemset/%s/metadata";
	public static final String COLLECTION_META = HOST + "/api/collection/%s/metadata";
	public static final String ENTRIES_META = HOST + "/api/media/logtrackitem/%s/metadata";
	
	//helpers to metadata?
	public static final String ITEM_HELPER_METADATA_URL = HOST + "/api/asset/item/%s";
	
	public static final String AUTOCOMPLETE_OFFER_URL = HOST + "/api/search/suggest/%s?num=5&qAutocomplete=1&qProfile=%s&qSynonym=1&krrr=0.9741306724026799&User=uuser&Passwd=inmedia";
	public static final String EMPTY_SEARCH = HOST + "/api/search/item?num=10&start=1&qProfile=item-default&qFacet=on&qHighlightMode=vms&krrr=0.7290897415950894";
	public static final String ENTITY_SEARCH = HOST + "/api/search/item?qProfile=item-default&qFacet=on&qHighlightMode=vms";
	public static final String FACETS_SEARCH = HOST + "/api/search/item?qProfile=item-default&qFacet=on&qHighlightMode=vms&search.default=%s";
	public static final String PROFILE_DETAIL = HOST + "/api/search/profile";
	public static final String COLLECTION_SEARCH = HOST + "/api/search/collection?num=10&start=1&qProfile=%s&qFacet=on&qHighlightMode=vms";
	public static final String ITEM_SEARCH = HOST + "/api/search/item?qProfile=%s&qFacet=on&qHighlightMode=vms";
	public static final String PACKAGES_SEARCH = HOST + "/api/search/itemset?num=10&start=1&qProfile=%s&qFacet=on&qHighlightMode=vms";
	public static final String ENTRIES_SEARCH = HOST + "/api/search/logtrackitem?num=10&start=1&qProfile=%s&qFacet=on&qHighlightMode=vms";
	
	
//	public static final String FOLDER_SEARCH = HOST + "/api/search/collection?num=10&start=1&qProfile=%s&qFacet=on&qHighlightMode=vms";

	
	
}
