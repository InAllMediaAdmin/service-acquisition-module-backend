package com.iam.serviceacquisition.domain;

public class AdhocClosestTimeZone {

    private AdhocCity adhocCity;
    private Float distance;

    public AdhocClosestTimeZone(final AdhocCity adhocCity, final Float amount) {
        this.adhocCity = adhocCity;
        this.distance = amount;
    }

    public AdhocCity getAdhocCity() {
        return adhocCity;
    }

    public Float getDistance() {
        return distance;
    }
}
