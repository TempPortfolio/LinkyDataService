package fr.linkyproject.dataservice.entries;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Objet contenant les données:
 * 	- temps       : en milliseconde (Unix Timestamp)
 * 	- temperature : en degré celsius
 * 	- humidité    : en %
 */
@Getter
@RequiredArgsConstructor
public class DHTEntry {
	private final long time;
	private final double temperature;
	private final double humidity;
}
