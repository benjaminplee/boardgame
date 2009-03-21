package net.todd.games.boardgame;

import javax.media.j3d.Bounds;
import javax.media.j3d.Node;
import javax.vecmath.Vector3f;

import net.todd.common.uitools.IListener;
import net.todd.common.uitools.ListenerManager;

public class UserPiecesView implements IUserPiecesView {
	private Piece selectedPiece;
	private final ListenerManager pieceSelectedListeners = new ListenerManager();
	private final Bounds bounds;
	private final IBranchGroup allPiecesBranchGroup;

	public UserPiecesView(Bounds bounds, final IPicker picker) {
		this.bounds = bounds;
		this.allPiecesBranchGroup = new BranchGroupFactory().createBranchGroup();

		picker.addListener(new IListener() {
			public void fireEvent() {
				Node selectedNode = picker.getSelectedNode();
				if (selectedNode instanceof UserPiece) {
					UserPiece node = (UserPiece) selectedNode;
					selectedPiece = node.getPiece();
					pieceSelectedListeners.notifyListeners();
				}
			}
		});
	}

	public void addPiece(Vector3f startingPoint) {
		Piece piece = new Piece(bounds, startingPoint);
		allPiecesBranchGroup.addChild(piece);
	}

	public void addPieceSelectedListener(IListener listener) {
		pieceSelectedListeners.addListener(listener);
	}

	public Piece getSelectedPiece() {
		return selectedPiece;
	}

	public IBranchGroup getBranchGroup() {
		return allPiecesBranchGroup;
	}

	public void movePieceTo(Vector3f position) {
		if (selectedPiece != null) {
			selectedPiece.movePieceTo(position);
			selectedPiece = null;
		}
	}
}
