
package com.appc.util;

public class CalculateDistanceUtil {
	private final double R = 6378137.0D;
	private static double EARTH_RADIUS = 6378.1369999999997D;


	private static double rad(double d) {
		return (d * 3.141592653589793D / 180.0D);
	}

	private double gps2m(double lat_a, double lng_a, double lat_b, double lng_b) {
		double radLat1 = rad(lat_a);
		double radLat2 = rad(lat_b);
		double a = radLat1 - radLat2;
		double b = rad(lng_a) - rad(lng_b);
		double s = 2.0D * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2.0D), 2.0D)
				+ Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2.0D), 2.0D)));

		s *= 6378137.0D;
		s = Math.round(s * 10000.0D) / 10000L;
		return s;
	}

	public static double gps2Km(double lat1, double lng1, double lat2, double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2.0D * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2.0D), 2.0D)
				+ Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2.0D), 2.0D)));

		s *= EARTH_RADIUS;
		s = Math.round(s * 10000.0D) / 10000L;
		return s;
	}
}