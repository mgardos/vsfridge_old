/*
   This file is part of the Virtual Smart Fridge.

   Virtual Smart Fridge is a software that emulates the basic functionality
   of a truly smart fridge.
   Copyright (C) 2018 Mariano E. Gardos Carro

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   The Virtual Smart Fridge is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package ar.net.mgardos;

import java.util.ArrayList;
import java.util.List;

public class VirtualSmartFridge implements SmartFridge {
    private FridgeDoor door;
    private SmartFridgeInspector inspector;
    private SmartFridgeState state;
    private List<Product> unidentifiedProducts;
    private List<Food> food;

    public VirtualSmartFridge(SmartFridgeState fs) {
        state = fs;
        unidentifiedProducts = new ArrayList<Product>(0);
        food = new ArrayList<Food>(0);
    }

    @Override
    public void turnOn(SmartFridgeFactory factory) {
        if (!state.isTurnedOn()) {
            door = factory.createDoor();
            inspector = factory.createInspector();
        }
        state.turnOn(this);
    }

    @Override
    public void open() {
        if (state.isTurnedOn() && !door.isOpened()) {
            door.open();
        }
    }

    @Override
    public void close() {
        if (state.isTurnedOn() && door.isOpened()) {
            door.close();
        }

        if (state.isTurnedOn() && !state.isInspectionDone()) {
            List<Snapshot> snapshots = inspector.survey(unidentifiedProducts);
            if (snapshots.isEmpty()) {
                unidentifiedProducts.clear();
            }
        }
    }

    public Boolean hasUnidentifiedProducts() {
        return !unidentifiedProducts.isEmpty();
    }

    public boolean hasFood() {
        return !food.isEmpty();
    }
}
