package Location;
//immutable
/**
 * 
 * ʵ��һ���ɻ�����ADT
 *
 */
public class FlightLocation implements Location{
	private String name;
	private String lng;
	private String lat;
	private boolean sharing;
	//AF
	//name����ɻ�վ���ƣ�lng����ɻ����ľ��ȣ�lat�����վ��γ��
	//sharing����ɻ����Ƿ�ɹ���
	//RI
	//name��=null
	//sharing=true
	//Safety from rep exposure
	//String boolean���ǲ��ɱ���������
	private void checkRep() {
		assert name!=null;
		assert sharing==true;
	}
	/**
	 * �½�һ���ɻ�������
	 * @param name �ɻ���������
	 * @param lng �ɻ�������
	 * @param lat �ɻ���γ��
	 * @param sharing �Ƿ�ɹ���
	 */
	public FlightLocation(String name,String lng,String lat,boolean sharing) {
		this.name=name;
		this.lng=lng;
		this.lat=lat;
		this.sharing=sharing;
		checkRep();
	}
	@Override
	public String getName() {
		checkRep();
		return this.name;
	}

	@Override
	public boolean share() {
		checkRep();
		return this.sharing;
	}

	@Override
	public String lng() {
		checkRep();
		return this.lng;
	}

	@Override
	public String lat() {
		checkRep();
		return this.lat;
	}
	/**
	 * Override equals����
	 */
	@Override
	public boolean equals(Object thatone) {
		if(! (thatone instanceof FlightLocation)) {
			return false;
		}
		FlightLocation that=(FlightLocation) thatone;
		return this.name.equals(that.name);
	}
	/**
	 * Override hashcode����
	 */
	@Override
	public int hashCode() {
		int h=1;
		h=31*h+this.name.hashCode();
		return h;
	}

}
