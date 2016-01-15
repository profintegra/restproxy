package com.constants;

public final class UrlConstants {
	
	public static final String HOST = "http://mgr.vizrt.it";
	public static final String EDIT_METADATA_URL = HOST + "/api/metadata/form/hubitem/r1";
	public static final String METADATA_URL = HOST + "/api/asset/item/%s/metadata";
	public static final String AUTOCOMPLETE_OFFER_URL = HOST + "/api/search/suggest/%s?num=5&qAutocomplete=1&qProfile=item-default&qSynonym=1&krrr=0.9741306724026799&User=uuser&Passwd=inmedia";
	public static final String EMPTY_SEARCH = HOST + "/api/search/item?num=10&start=1&qProfile=item-default&qFacet=on&qHighlightMode=vms&krrr=0.7290897415950894";
	public static final String ENTITY_SEARCH = HOST + "/api/search/item?num=10&start=1&qProfile=item-default&qFacet=on&qHighlightMode=vms&search.default=%s&krrr=0.7290897415950894&User=uuser&Passwd=inmedia";
	
}
