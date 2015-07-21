#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import com.dayatang.domain.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 用于记录IP相关的记录
 * User: zjzhai
 * Date: 13-4-2
 * Time: 下午3:15
 */
@Embeddable
public class IpInfo implements ValueObject {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "ipinfo_ip")
    private String ip;

    @Column(name = "ipinfo_country")
    private String country;

    @Column(name = "ipinfo_province")
    private String province;

    public IpInfo(){

    }

    public static IpInfo createBy(String ip){
        IpInfo ipInfo = new IpInfo();

        ipInfo.setIp(ip);

        return ipInfo;
    }

    @Override
    public String toString() {
        return "IpInfo{" +
                "ip='" + ip + '${symbol_escape}'' +
                ", country='" + country + '${symbol_escape}'' +
                ", province='" + province + '${symbol_escape}'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IpInfo)) return false;

        IpInfo ipInfo = (IpInfo) o;

        if (country != null ? !country.equals(ipInfo.country) : ipInfo.country != null) return false;
        if (ip != null ? !ip.equals(ipInfo.ip) : ipInfo.ip != null) return false;
        if (province != null ? !province.equals(ipInfo.province) : ipInfo.province != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ip != null ? ip.hashCode() : 0;
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        return result;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


}
