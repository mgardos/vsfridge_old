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

import java.util.List;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.Times;

public class VirtualSmartFridgeTest {
    @Mock
    private FridgeDoor fridgeDoor;

    @Mock
    private SmartFridgeInspector inspector;

    @Mock
    private SmartFridgeFactory fridgeFactory;

    @Mock
    private SmartFridgeState fridgeState;

    @Mock
    private Snapshot snapshot;

    private VirtualSmartFridge vsf;
    private List<Snapshot> snapshots;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        vsf = new VirtualSmartFridge(fridgeState);
        snapshots = new ArrayList<Snapshot>(0);
    }

    @Test
    public void turn_on_with_door_opened_and_no_products_inside() {
        when(fridgeFactory.createDoor()).thenReturn(fridgeDoor);
        when(fridgeFactory.createInspector()).thenReturn(inspector);
        when(fridgeState.isTurnedOn()).thenReturn(Boolean.FALSE, Boolean.TRUE);
        when(fridgeDoor.isOpened()).thenReturn(Boolean.FALSE, Boolean.TRUE, Boolean.FALSE);
        when(inspector.survey(isA(List.class))).thenReturn(snapshots);

        vsf.turnOn(fridgeFactory);
        vsf.open();
        vsf.close();

        verify(fridgeState, new Times(2)).isTurnedOn();
        verify(fridgeState).turnOn(isA(SmartFridge.class));
        verify(fridgeFactory).createDoor();
        verify(fridgeFactory).createInspector();
        verify(fridgeDoor, new Times(3)).isOpened();
        verify(fridgeDoor).open();
        verify(fridgeDoor).close();
        verify(inspector).survey(isA(List.class));

        assertFalse(vsf.hasUnidentifiedProducts());
    }
}
