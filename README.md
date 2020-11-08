# Road-Map-Path-Finder

Objective of the program is to find a path between two specified points in a road map, if such a path exists. Some of the roads in the map are free and some are toll roads; the use of a toll road costs 1 dollar. Given a starting point, destination and amount of money available to pay for toll roads, the program tries to find a path that uses the most toll roads and shortest path.

The program stores the road map as a unidirected graph implemented using an adjacency matrix. Used a modified depth first search algorithm to recursively search all routes until the following conditions are met: destination is reached, you have not exceeded the amount of money available to pay for toll roads
