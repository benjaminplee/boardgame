package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector3f;

import net.todd.common.uitools.IListener;

import org.junit.Before;
import org.junit.Test;

public class GameGridModelTest {
	private Vector3f positionSelected;

	@Before
	public void setUp() {
		positionSelected = null;
	}

	@Test
	public void testGetTileDataPullsFromGridData() {
		TileData[][] tileData = new TileData[][] {};
		GameGridDataMock gameGridData = new GameGridDataMock();
		gameGridData.tileData = tileData;
		GameGridModel model = new GameGridModel(gameGridData);
		ComparisonUtil.compareDoubleArrays(tileData, model.getTileData());
	}

	@Test
	public void testTeamOneStartingPositionIsInCorrectPosition3() {
		GameGridDataMock gameGridData = new GameGridDataMock();
		GameGridModel model = new GameGridModel(gameGridData);
		gameGridData.teamOneStartingPosition.add(new Vector3f(new float[] { 1f, 2f, 3f }));
		gameGridData.teamOneStartingPosition.add(new Vector3f(new float[] { 4f, 5f, 6f }));

		assertEquals(new Vector3f(new float[] { 1f, 2f, 3f }), model
				.getTeamOneStartingGridPositions().get(0));
		assertEquals(new Vector3f(new float[] { 4f, 5f, 6f }), model
				.getTeamOneStartingGridPositions().get(1));
	}

	@Test
	public void testModelNotifiesListenersWhenAGridPositionIsSelected() {
		GameGridModel model = new GameGridModel(null);
		PositionSelectedListenerStub listenerStub1 = new PositionSelectedListenerStub();
		PositionSelectedListenerStub listenerStub2 = new PositionSelectedListenerStub();
		model.addPositionSelectedListener(listenerStub1);
		model.addPositionSelectedListener(listenerStub2);
		
		assertFalse(listenerStub1.eventFired);
		assertFalse(listenerStub2.eventFired);
		
		TileData tileData = new TileData();
		tileData.setPosition(new float[] { 1f, 2f, 3f });
		model.setSelectedTile(tileData);
		
		assertTrue(listenerStub1.eventFired);
		assertTrue(listenerStub2.eventFired);
	}

	@Test
	public void testModelMakesSelectedPositionInformationAvailableBeforeNotifyingListeners() {
		final GameGridModel model = new GameGridModel(null);
		model.addPositionSelectedListener(new IListener() {
			public void fireEvent() {
				positionSelected = model.getSelectedPosition();
			}
		});

		TileData tileData = new TileData();
		tileData.setPosition(new float[] { 1f, 2f, 3f });
		model.setSelectedTile(tileData);
		assertEquals(new Vector3f(tileData.getPosition()), positionSelected);
	}

	private static class GameGridDataMock extends GameGridData {
		List<Vector3f> teamOneStartingPosition = new ArrayList<Vector3f>();
		private TileData[][] tileData;

		@Override
		public TileData[][] getTileData() {
			return tileData;
		}

		@Override
		public List<Vector3f> getTeamOneStartingPositions() {
			return teamOneStartingPosition;
		}
	}

	private static class PositionSelectedListenerStub implements IListener {
		private boolean eventFired;

		public void fireEvent() {
			eventFired = true;
		}
	}
}