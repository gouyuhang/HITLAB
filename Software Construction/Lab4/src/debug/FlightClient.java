package debug;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Note that this class may use the other two class: Flight and Plane.
 * 
 * Debug and fix errors. DON'T change the initial logic of the code.
 *
 */
public class FlightClient {

	/**
	 * Given a list of flights and a list of planes, suppose each flight has not yet
	 * been allocated a plane to, this method tries to allocate a plane to each
	 * flight and ensures that there're no any time conflicts between all the
	 * allocations. For example: Flight 1 (2020-01-01 8:00-10:00) and Flight 2
	 * (2020-01-01 9:50-10:40) are all allocated the same plane B0001, then there's
	 * conflict because from 9:50 to 10:00 the plane B0001 cannot serve for the two
	 * flights simultaneously.
	 * 
	 * @param planes  a list of planes
	 * @param flights a list of flights each of which has no plane allocated
	 * @return false if there's no allocation solution that could avoid any
	 *         conflicts
	 */

	public boolean planeAllocation(List<Plane> planes, List<Flight> flights) {
		boolean bFeasible = true;
		Random r = new Random();
		for (Flight f : flights) {
			boolean bAllocated = false;
			List<String> visit = new ArrayList<>();
			while (!bAllocated) {
				Plane p = planes.get(r.nextInt(planes.size()));
				if (visit.size() == planes.size()) {
					return false;
				}
				if (!visit.contains(p.getPlaneNo())) {
					visit.add(p.getPlaneNo());
				} else {
					continue;
				}
				Calendar fStart = f.getDepartTime();
				Calendar fEnd = f.getArrivalTime();
				boolean bConflict = false;
				for (Flight t : flights) {
					Plane q = t.getPlane();
					if (q != null) {
						if (!q.equals(p)) {
							continue;
						}
					}
					if (t.equals(f)) {
						continue;
					}
					Calendar tStart = t.getDepartTime();
					Calendar tEnd = t.getArrivalTime();

					if ((tStart.compareTo(fStart) <= 0 && fStart.compareTo(tEnd) <= 0)
							|| (fStart.compareTo(tStart) <= 0 && tStart.compareTo(fEnd) <= 0)) {
						bConflict = true;
						break;
					}
				}

				if (!bConflict) {
					f.setPlane(p);
					break;
				}
			}
		}

		return bFeasible;
	}
}
