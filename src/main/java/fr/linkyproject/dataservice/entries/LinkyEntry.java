package fr.linkyproject.dataservice.entries;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Object contenant les données brutes
 * du compteur linky.
 */
@Getter
@RequiredArgsConstructor
public class LinkyEntry {
	private final long time;
	private final String id;               // ADCO
	
	private final int subscribeIntensity;  // ISOUSC
	
	private final int baseHourIndex;       // BASE
	private final int fullHourIndex;       // HCHP
	private final int offpeakHourIndex;    // HCHC
	
	private final int maxIntensity;        // IMAX
	private final int instantIntensity;    // IINST
	
	private final int currentTariffOption; // PTEC
}