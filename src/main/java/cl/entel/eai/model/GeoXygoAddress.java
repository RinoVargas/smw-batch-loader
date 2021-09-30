package cl.entel.eai.model;

import oracle.spatial.geometry.JGeometry;

public class GeoXygoAddress {
    private long id;
    private JGeometry geometry;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public JGeometry getGeometry() {
        return geometry;
    }

    public void setGeometry(JGeometry geometry) {
        this.geometry = geometry;
    }
}
