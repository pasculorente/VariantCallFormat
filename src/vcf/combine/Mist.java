/*
 * Copyright (c) UICHUIMI 2016
 *
 * This file is part of VariantCallFormat.
 *
 * VariantCallFormat is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * VariantCallFormat is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Foobar.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package vcf.combine;

import vcf.Variant;

import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by uichuimi on 8/06/16.
 */
public class Mist {

    //    private TreeMap<String, BitSet> regions = new TreeMap<>();
    private TreeMap<String, TreeSet<MistRegion>> treeMap = new TreeMap<>();

    public void addRegion(String chrom, int start, int end) {
        treeMap.putIfAbsent(chrom, new TreeSet<>());
        treeMap.get(chrom).add(new MistRegion(start, end));
//        regions.putIfAbsent(chrom, new BitSet());
//        final BitSet positions = regions.get(chrom);
//        positions.set(start, end);
    }

    public boolean isInMistRegion(Variant variant) {
        return isInMistRegion(variant.getChrom(), variant.getPosition());
    }

    public boolean isInMistRegion(String chrom, int position) {
        if (!treeMap.containsKey(chrom)) return false;
        final TreeSet<MistRegion> mistRegions = treeMap.get(chrom);
        return mistRegions.stream().anyMatch(mistRegion -> mistRegion.contains(position));
//        return regions.containsKey(chrom) && regions.get(chrom).get(position);
    }

    private class MistRegion implements Comparable<MistRegion> {
        int start;
        int end;

        MistRegion(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(MistRegion other) {
            final int compare = Integer.compare(start, other.start);
            return compare == 0 ? 0 : Integer.compare(end, other.end);
        }

        public boolean contains(int position) {
            return start <= position && position <= end;
        }
    }
}
