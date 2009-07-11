package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import net.todd.common.uitools.IListener;

import org.junit.Before;
import org.junit.Test;

public class UserPiecePresenterTest {
	private UserPieceViewStub view;
	private UserPieceModelStub model;

	@Before
	public void setUp() {
		view = new UserPieceViewStub();
		model = new UserPieceModelStub();
	}

	@Test
	public void testPresenterGetsPiecesFromModelAndAddsAllPiecesToView() {
		PieceInfo pieceInfo1 = PieceFixture.createPieceInfo();
		PieceInfo pieceInfo2 = PieceFixture.createPieceInfo();
		PieceInfo pieceInfo3 = PieceFixture.createPieceInfo();
		model.allPieces.add(pieceInfo1);
		model.allPieces.add(pieceInfo2);
		model.allPieces.add(pieceInfo3);

		assertEquals(0, view.startingPositions.size());

		new UserPiecesPresenter(view, model);

		assertEquals(3, view.startingPositions.size());
		assertEquals(pieceInfo1.getPosition(), view.startingPositions.get(0));
		assertEquals(pieceInfo2.getPosition(), view.startingPositions.get(1));
		assertEquals(pieceInfo3.getPosition(), view.startingPositions.get(2));
	}

	@Test
	public void testWhenPieceSelectedOnViewModelGetsNotified() {
		PieceGroupStub pieceGroup = new PieceGroupStub();
		view.selectedPiece = pieceGroup;
		new UserPiecesPresenter(view, model);
		assertNull(model.selectedPiece);

		view.pieceSelectedListener.fireEvent();

		assertSame(pieceGroup, model.selectedPiece);
	}

	private static class UserPieceViewStub implements IUserPiecesView {
		IListener pieceSelectedListener;
		IPieceGroup selectedPiece;
		List<Vector3f> startingPositions = new ArrayList<Vector3f>();

		public void addPiece(PieceInfo pieceInfo) {
			startingPositions.add(pieceInfo.getPosition());
		}

		public void addPieceSelectedListener(IListener listener) {
			pieceSelectedListener = listener;
		}

		public IPieceGroup getSelectedPiece() {
			return selectedPiece;
		}

		public IBranchGroup getBranchGroup() {
			throw new UnsupportedOperationException();
		}
	}

	private static class UserPieceModelStub implements IUserPiecesModel {
		IPieceGroup selectedPiece;
		IListener userModelListener;
		Vector3f position;
		final List<PieceInfo> allPieces = new ArrayList<PieceInfo>();

		public void addMoveListener(IListener listener) {
			userModelListener = listener;
		}

		public List<PieceInfo> getAllPieces() {
			return allPieces;
		}

		public Vector3f getMoveToLocation() {
			return position;
		}

		public void setSelectedPiece(IPieceGroup selectedPieceId) {
			this.selectedPiece = selectedPieceId;
		}
	}

	private static class PieceGroupStub implements IPieceGroup {
		public Color3f getColor() {
			return null;
		}

		public PieceInfo getPieceInfo() {
			return null;
		}

		public void movePieceAlongPath(List<Vector3f> path) {
		}
	}
}
