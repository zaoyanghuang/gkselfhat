package com.gnw.pojo;

public class IndoorMap {
    private int imid;
    private String mapName;
    private int floorNum;
    private String leftUpLatLng;
    private String leftDownLatLng;
    private String rightUpLatLng;
    private String rightDonwLatLng;
    private String leftUpPixel;
    private String letfDownPixel;
    private String rightUpPixel;
    private String rightDownPixel;
    private int companyId;
    private String imagePath;
    private int useState;

    public IndoorMap() {
    }

    public IndoorMap(int imid, String mapName, int floorNum, String leftUpLatLng, String leftDownLatLng, String rightUpLatLng, String rightDonwLatLng, String leftUpPixel, String letfDownPixel, String rightUpPixel, String rightDownPixel, int companyId, String imagePath, int useState) {
        this.imid = imid;
        this.mapName = mapName;
        this.floorNum = floorNum;
        this.leftUpLatLng = leftUpLatLng;
        this.leftDownLatLng = leftDownLatLng;
        this.rightUpLatLng = rightUpLatLng;
        this.rightDonwLatLng = rightDonwLatLng;
        this.leftUpPixel = leftUpPixel;
        this.letfDownPixel = letfDownPixel;
        this.rightUpPixel = rightUpPixel;
        this.rightDownPixel = rightDownPixel;
        this.companyId = companyId;
        this.imagePath = imagePath;
        this.useState = useState;
    }

    public int getImid() {
        return imid;
    }

    public void setImid(int imid) {
        this.imid = imid;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public int getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(int floorNum) {
        this.floorNum = floorNum;
    }

    public String getLeftUpLatLng() {
        return leftUpLatLng;
    }

    public void setLeftUpLatLng(String leftUpLatLng) {
        this.leftUpLatLng = leftUpLatLng;
    }

    public String getLeftDownLatLng() {
        return leftDownLatLng;
    }

    public void setLeftDownLatLng(String leftDownLatLng) {
        this.leftDownLatLng = leftDownLatLng;
    }

    public String getRightUpLatLng() {
        return rightUpLatLng;
    }

    public void setRightUpLatLng(String rightUpLatLng) {
        this.rightUpLatLng = rightUpLatLng;
    }

    public String getRightDonwLatLng() {
        return rightDonwLatLng;
    }

    public void setRightDonwLatLng(String rightDonwLatLng) {
        this.rightDonwLatLng = rightDonwLatLng;
    }

    public String getLeftUpPixel() {
        return leftUpPixel;
    }

    public void setLeftUpPixel(String leftUpPixel) {
        this.leftUpPixel = leftUpPixel;
    }

    public String getLetfDownPixel() {
        return letfDownPixel;
    }

    public void setLetfDownPixel(String letfDownPixel) {
        this.letfDownPixel = letfDownPixel;
    }

    public String getRightUpPixel() {
        return rightUpPixel;
    }

    public void setRightUpPixel(String rightUpPixel) {
        this.rightUpPixel = rightUpPixel;
    }

    public String getRightDownPixel() {
        return rightDownPixel;
    }

    public void setRightDownPixel(String rightDownPixel) {
        this.rightDownPixel = rightDownPixel;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getUseState() {
        return useState;
    }

    public void setUseState(int useState) {
        this.useState = useState;
    }

    @Override
    public String toString() {
        return "IndoorMap{" +
                "imid=" + imid +
                ", mapName='" + mapName + '\'' +
                ", floorNum=" + floorNum +
                ", leftUpLatLng='" + leftUpLatLng + '\'' +
                ", leftDownLatLng='" + leftDownLatLng + '\'' +
                ", rightUpLatLng='" + rightUpLatLng + '\'' +
                ", rightDonwLatLng='" + rightDonwLatLng + '\'' +
                ", leftUpPixel='" + leftUpPixel + '\'' +
                ", letfDownPixel='" + letfDownPixel + '\'' +
                ", rightUpPixel='" + rightUpPixel + '\'' +
                ", rightDownPixel='" + rightDownPixel + '\'' +
                ", companyId=" + companyId +
                ", imagePath='" + imagePath + '\'' +
                ", useState=" + useState +
                '}';
    }
}
