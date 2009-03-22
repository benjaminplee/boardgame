package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Node;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import net.todd.common.uitools.IListener;

import org.junit.Test;

public class UserPiecesViewTest {
	private final Bounds bounds = new BoundingSphere(new Point3d(0, 0, 0), 100);

	@Test
	public void testSelectingAPieceNotifiesViewsListenersAndPopulatesTheSelectedPiece() {
		PickerStub picker = new PickerStub();
		Piece piece = UserPieceFixture.getPiece();
		picker.selectedNode = new UserPiece(piece);
		IBranchGroupFactory branchGroupFactory = new BranchGroupFactoryStub();
		UserPiecesView userPiecesView = new UserPiecesView(bounds, picker, branchGroupFactory);
		ListenerStub listener1 = new ListenerStub();
		ListenerStub listener2 = new ListenerStub();
		userPiecesView.addPieceSelectedListener(listener1);
		userPiecesView.addPieceSelectedListener(listener2);

		assertFalse(listener1.eventFired);
		assertFalse(listener2.eventFired);
		assertNull(userPiecesView.getSelectedPiece());

		picker.listener.fireEvent();

		assertTrue(listener1.eventFired);
		assertTrue(listener2.eventFired);
		assertSame(piece, userPiecesView.getSelectedPiece());
	}

	@Test
	public void testAddingPieceAddsItToTheBranchGroup() {
		PickerStub picker = new PickerStub();
		Piece piece = UserPieceFixture.getPiece();
		picker.selectedNode = new UserPiece(piece);
		BranchGroupStub branchGroup = new BranchGroupStub();
		BranchGroupFactoryStub branchGroupFactory = new BranchGroupFactoryStub();
		branchGroupFactory.branchGroup = branchGroup;
		UserPiecesView userPiecesView = new UserPiecesView(bounds, picker, branchGroupFactory);

		userPiecesView.addPiece(new Vector3f());
		userPiecesView.addPiece(new Vector3f());

		assertSame(branchGroup, userPiecesView.getBranchGroup());
		assertEquals(2, branchGroup.children.size());
	}

	private static class PickerStub implements IPicker {
		IListener listener;
		Node selectedNode;

		public void addListener(IListener listener) {
			this.listener = listener;
		}

		public Node getSelectedNode() {
			return selectedNode;
		}
	}

	private static class ListenerStub implements IListener {
		boolean eventFired;

		public void fireEvent() {
			eventFired = true;
		}
	}

	private static class BranchGroupFactoryStub implements IBranchGroupFactory {
		IBranchGroup branchGroup;

		public IBranchGroup createBranchGroup() {
			return branchGroup;
		}
	}

	private static class BranchGroupStub implements IBranchGroup {
		List<Node> children = new ArrayList<Node>();

		public void addChild(Node node) {
			children.add(node);
		}

		public void addChild(IBranchGroup child) {
			throw new UnsupportedOperationException();
		}

		public void compile() {
			throw new UnsupportedOperationException();
		}

		public BranchGroup getInternal() {
			throw new UnsupportedOperationException();
		}
	}
}
