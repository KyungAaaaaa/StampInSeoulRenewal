package com.example.gotothefestival.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ThemeData {

    @SerializedName("response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }


    public class Header {

        @SerializedName("resultCode")
        @Expose
        private String resultCode;
        @SerializedName("resultMsg")
        @Expose
        private String resultMsg;

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

        public String getResultMsg() {
            return resultMsg;
        }

        public void setResultMsg(String resultMsg) {
            this.resultMsg = resultMsg;
        }

    }

    public class Body {

        @SerializedName("items")
        @Expose
        private Items items;
        @SerializedName("numOfRows")
        @Expose
        private Integer numOfRows;
        @SerializedName("pageNo")
        @Expose
        private Integer pageNo;
        @SerializedName("totalCount")
        @Expose
        private Integer totalCount;

        public Items getItems() {
            return items;
        }

        public void setItems(Items items) {
            this.items = items;
        }

        public Integer getNumOfRows() {
            return numOfRows;
        }

        public void setNumOfRows(Integer numOfRows) {
            this.numOfRows = numOfRows;
        }

        public Integer getPageNo() {
            return pageNo;
        }

        public void setPageNo(Integer pageNo) {
            this.pageNo = pageNo;
        }

        public Integer getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(Integer totalCount) {
            this.totalCount = totalCount;
        }

    }


    public class Item implements Parcelable {

        public Item(String title) {
            this.title = title;
        }
        public Item(double mapX, double mapY) {
            this.mapx = mapX;
            this.mapy = mapY;
        }
        public Item(String title, String addr, double mapX, double mapY, String firstImage) {
            this.title = title;
            this.addr1 = addr;
            this.mapx = mapX;
            this.mapy = mapY;
            this.firstimage = firstImage;
        }

        public Item(int key, String title, String firstImage, String addr1, String picture, String content_pola, String content_title, String contents) {
            this.key = key;
            this.title = title;
            this.firstimage = firstImage;
            this.addr1 = addr1;
            this.picture = picture;
            this.content_pola = content_pola;
            this.content_title = content_title;
            this.contents = contents;
        }

        public Item(String title, String picture, String content_pola, String content_title, String contents, int complete) {

            this.title = title;
            this.picture = picture;
            this.content_pola = content_pola;
            this.content_title = content_title;
            this.contents = contents;
            this.complete = complete;
        }

        public Item(String title, String firstImage, String picture, String content_pola, String content_title, String contents, int complete) {

            this.title = title;
            this.firstimage = firstImage;
            this.picture = picture;
            this.content_pola = content_pola;
            this.content_title = content_title;
            this.contents = contents;
            this.complete = complete;
        }


        public Item(String title, String addr, double mapX, double mapY) {
            this.title = title;
            this.addr1 = addr;
            this.mapx = mapX;
            this.mapy = mapY;
        }

        public Item() {
        }



        protected Item(Parcel in) {
            hart = in.readByte() != 0;
            addr1 = in.readString();
            addr2 = in.readString();
            if (in.readByte() == 0) {
                areacode = null;
            } else {
                areacode = in.readInt();
            }
            cat1 = in.readString();
            cat2 = in.readString();
            cat3 = in.readString();
            if (in.readByte() == 0) {
                contentid = null;
            } else {
                contentid = in.readInt();
            }
            if (in.readByte() == 0) {
                contenttypeid = null;
            } else {
                contenttypeid = in.readInt();
            }
            if (in.readByte() == 0) {
                createdtime = null;
            } else {
                createdtime = in.readLong();
            }
            if (in.readByte() == 0) {
                eventenddate = null;
            } else {
                eventenddate = in.readLong();
            }
            if (in.readByte() == 0) {
                eventstartdate = null;
            } else {
                eventstartdate = in.readLong();
            }
            firstimage = in.readString();
            firstimage2 = in.readString();
            if (in.readByte() == 0) {
                mapx = null;
            } else {
                mapx = in.readDouble();
            }
            if (in.readByte() == 0) {
                mapy = null;
            } else {
                mapy = in.readDouble();
            }
            if (in.readByte() == 0) {
                mlevel = null;
            } else {
                mlevel = in.readInt();
            }
            if (in.readByte() == 0) {
                modifiedtime = null;
            } else {
                modifiedtime = in.readLong();
            }
            if (in.readByte() == 0) {
                readcount = null;
            } else {
                readcount = in.readInt();
            }
            if (in.readByte() == 0) {
                sigungucode = null;
            } else {
                sigungucode = in.readInt();
            }
            tel = in.readString();
            title = in.readString();
            key = in.readInt();
            picture = in.readString();
            content_pola = in.readString();
            content_title = in.readString();
            contents = in.readString();
            complete = in.readInt();
            overview = in.readString();
            telname = in.readString();
            zipcode = in.readString();
        }

        public final Creator<Item> CREATOR = new Creator<Item>() {
            @Override
            public Item createFromParcel(Parcel in) {
                return new Item(in);
            }

            @Override
            public Item[] newArray(int size) {
                return new Item[size];
            }
        };


        private boolean hart = false;
        @SerializedName("addr1")
        @Expose
        private String addr1;
        @SerializedName("addr2")
        @Expose
        private String addr2;
        @SerializedName("areacode")
        @Expose
        private Integer areacode;
        @SerializedName("cat1")
        @Expose
        private String cat1;
        @SerializedName("cat2")
        @Expose
        private String cat2;
        @SerializedName("cat3")
        @Expose
        private String cat3;
        @SerializedName("contentid")
        @Expose
        private Integer contentid;
        @SerializedName("contenttypeid")
        @Expose
        private Integer contenttypeid;
        @SerializedName("createdtime")
        @Expose
        private Long createdtime;
        @SerializedName("eventenddate")
        @Expose
        private Long eventenddate;
        @SerializedName("eventstartdate")
        @Expose
        private Long eventstartdate;
        @SerializedName("firstimage")
        @Expose
        private String firstimage;
        @SerializedName("firstimage2")
        @Expose
        private String firstimage2;
        @SerializedName("mapx")
        @Expose
        private Double mapx;
        @SerializedName("mapy")
        @Expose
        private Double mapy;
        @SerializedName("mlevel")
        @Expose
        private Integer mlevel;
        @SerializedName("modifiedtime")
        @Expose
        private Long modifiedtime;
        @SerializedName("readcount")
        @Expose
        private Integer readcount;
        @SerializedName("sigungucode")
        @Expose
        private Integer sigungucode;
        @SerializedName("tel")
        @Expose
        private String tel;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("overview")
        @Expose
        private String overview;
        @SerializedName("telname")
        @Expose
        private String telname;
        @SerializedName("zipcode")
        @Expose
        private String zipcode;

        private int key;
        private String picture;
        private String content_pola;
        private String content_title;
        private String contents;
        private int complete;
/////////////////////////////////////////////////////////////////
        public boolean isHart() {
            return hart;
        }

        public void setHart(boolean hart) {
            this.hart = hart;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getContent_pola() {
            return content_pola;
        }

        public void setContent_pola(String content_pola) {
            this.content_pola = content_pola;
        }

        public String getContent_title() {
            return content_title;
        }

        public void setContent_title(String content_title) {
            this.content_title = content_title;
        }

        public String getContents() {
            return contents;
        }

        public void setContents(String contents) {
            this.contents = contents;
        }

        public int getComplete() {
            return complete;
        }

        public void setComplete(int complete) {
            this.complete = complete;
        }



        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }







        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getAddr1() {
            return addr1;
        }

        public void setAddr1(String addr1) {
            this.addr1 = addr1;
        }

        public String getAddr2() {
            return addr2;
        }

        public void setAddr2(String addr2) {
            this.addr2 = addr2;
        }

        public Integer getAreacode() {
            return areacode;
        }

        public void setAreacode(Integer areacode) {
            this.areacode = areacode;
        }

        public String getCat1() {
            return cat1;
        }

        public void setCat1(String cat1) {
            this.cat1 = cat1;
        }

        public String getCat2() {
            return cat2;
        }

        public void setCat2(String cat2) {
            this.cat2 = cat2;
        }

        public String getCat3() {
            return cat3;
        }

        public void setCat3(String cat3) {
            this.cat3 = cat3;
        }

        public Integer getContentid() {
            return contentid;
        }

        public void setContentid(Integer contentid) {
            this.contentid = contentid;
        }

        public Integer getContenttypeid() {
            return contenttypeid;
        }

        public void setContenttypeid(Integer contenttypeid) {
            this.contenttypeid = contenttypeid;
        }

        public Long getCreatedtime() {
            return createdtime;
        }

        public void setCreatedtime(Long createdtime) {
            this.createdtime = createdtime;
        }

        public Long getEventenddate() {
            return eventenddate;
        }

        public void setEventenddate(Long eventenddate) {
            this.eventenddate = eventenddate;
        }

        public Long getEventstartdate() {
            return eventstartdate;
        }

        public void setEventstartdate(Long eventstartdate) {
            this.eventstartdate = eventstartdate;
        }

        public String getFirstimage() {
            return firstimage;
        }

        public void setFirstimage(String firstimage) {
            this.firstimage = firstimage;
        }

        public String getFirstimage2() {
            return firstimage2;
        }

        public void setFirstimage2(String firstimage2) {
            this.firstimage2 = firstimage2;
        }

        public Double getMapx() {
            return mapx;
        }

        public void setMapx(Double mapx) {
            this.mapx = mapx;
        }

        public Double getMapy() {
            return mapy;
        }

        public void setMapy(Double mapy) {
            this.mapy = mapy;
        }

        public Integer getMlevel() {
            return mlevel;
        }

        public void setMlevel(Integer mlevel) {
            this.mlevel = mlevel;
        }

        public Long getModifiedtime() {
            return modifiedtime;
        }

        public void setModifiedtime(Long modifiedtime) {
            this.modifiedtime = modifiedtime;
        }

        public Integer getReadcount() {
            return readcount;
        }

        public void setReadcount(Integer readcount) {
            this.readcount = readcount;
        }

        public Integer getSigungucode() {
            return sigungucode;
        }

        public void setSigungucode(Integer sigungucode) {
            this.sigungucode = sigungucode;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTelname() {
            return telname;
        }

        public void setTelname(String telname) {
            this.telname = telname;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeByte((byte) (hart ? 1 : 0));
            parcel.writeString(addr1);
            parcel.writeString(addr2);
            if (areacode == null) {
                parcel.writeByte((byte) 0);
            } else {
                parcel.writeByte((byte) 1);
                parcel.writeInt(areacode);
            }
            parcel.writeString(cat1);
            parcel.writeString(cat2);
            parcel.writeString(cat3);
            if (contentid == null) {
                parcel.writeByte((byte) 0);
            } else {
                parcel.writeByte((byte) 1);
                parcel.writeInt(contentid);
            }
            if (contenttypeid == null) {
                parcel.writeByte((byte) 0);
            } else {
                parcel.writeByte((byte) 1);
                parcel.writeInt(contenttypeid);
            }
            if (createdtime == null) {
                parcel.writeByte((byte) 0);
            } else {
                parcel.writeByte((byte) 1);
                parcel.writeLong(createdtime);
            }
            if (eventenddate == null) {
                parcel.writeByte((byte) 0);
            } else {
                parcel.writeByte((byte) 1);
                parcel.writeLong(eventenddate);
            }
            if (eventstartdate == null) {
                parcel.writeByte((byte) 0);
            } else {
                parcel.writeByte((byte) 1);
                parcel.writeLong(eventstartdate);
            }
            parcel.writeString(firstimage);
            parcel.writeString(firstimage2);
            if (mapx == null) {
                parcel.writeByte((byte) 0);
            } else {
                parcel.writeByte((byte) 1);
                parcel.writeDouble(mapx);
            }
            if (mapy == null) {
                parcel.writeByte((byte) 0);
            } else {
                parcel.writeByte((byte) 1);
                parcel.writeDouble(mapy);
            }
            if (mlevel == null) {
                parcel.writeByte((byte) 0);
            } else {
                parcel.writeByte((byte) 1);
                parcel.writeInt(mlevel);
            }
            if (modifiedtime == null) {
                parcel.writeByte((byte) 0);
            } else {
                parcel.writeByte((byte) 1);
                parcel.writeLong(modifiedtime);
            }
            if (readcount == null) {
                parcel.writeByte((byte) 0);
            } else {
                parcel.writeByte((byte) 1);
                parcel.writeInt(readcount);
            }
            if (sigungucode == null) {
                parcel.writeByte((byte) 0);
            } else {
                parcel.writeByte((byte) 1);
                parcel.writeInt(sigungucode);
            }
            parcel.writeString(tel);
            parcel.writeString(title);
            parcel.writeInt(key);
            parcel.writeString(picture);
            parcel.writeString(content_pola);
            parcel.writeString(content_title);
            parcel.writeString(contents);
            parcel.writeInt(complete);
            parcel.writeString(overview);
            parcel.writeString(telname);
            parcel.writeString(zipcode);
        }
    }

    public class Items {

        @SerializedName("item")
        @Expose
        private List<Item> item = null;

        public List<Item> getItem() {
            return item;
        }

        public void setItem(List<Item> item) {
            this.item = item;
        }

    }

    public class Response {

        @SerializedName("header")
        @Expose
        private Header header;
        @SerializedName("body")
        @Expose
        private Body body;

        public Header getHeader() {
            return header;
        }

        public void setHeader(Header header) {
            this.header = header;
        }

        public Body getBody() {
            return body;
        }

        public void setBody(Body body) {
            this.body = body;
        }

    }
}
