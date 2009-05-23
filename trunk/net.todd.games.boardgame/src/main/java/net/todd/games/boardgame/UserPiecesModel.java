package net.todd.games.boardgame;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector3f;

import net.todd.common.uitools.IListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UserPiecesModel implements IUserPiecesModel {
	private static final Log log = LogFactory.getLog(UserPiecesModel.class);

	private static final float PIECE_HEIGHT = 10f;
	private final List<PieceInfo> teamOnePieces;
	private final List<PieceInfo> teamTwoPieces;
	private IPieceGroup selectedPieceToMove;
	private final IGameGridModel gameGridModel;

	public UserPiecesModel(IGamePieceData gamePieceData, final IGameGridModel gameGridModel,
			final IMoveValidator moveValidator) {
		this.gameGridModel = gameGridModel;
		teamOnePieces = new ArrayList<PieceInfo>(adjustPiecesForHeight(gamePieceData
				.getTeamOnePieces()));
		teamTwoPieces = new ArrayList<PieceInfo>(adjustPiecesForHeight(gamePieceData
				.getTeamTwoPieces()));

		gameGridModel.addTileSelectedListener(new IListener() {
			public void fireEvent() {
				try {
					if (selectedPieceToMove != null) {
						Vector3f targetLocation = gameGridModel.getSelectedTileLocation();
						adjustPositionForHeight(targetLocation);
						moveValidator.confirmMove(selectedPieceToMove.getPieceInfo(),
								targetLocation);
						moveAction(selectedPieceToMove, targetLocation);
					}
				} catch (ValidMoveException e) {
					log.info(e.getMessage());
				}
			}
		});
	}

	private void moveAction(IPieceGroup selectedPieceToMove, Vector3f targetLocation) {
		selectedPieceToMove.movePieceTo(targetLocation);
	}

	private void adjustPositionForHeight(Vector3f position) {
		if (position != null) {
			position.y = PIECE_HEIGHT / 2;
		}
	}

	private List<PieceInfo> adjustPiecesForHeight(List<PieceInfo> pieces) {
		for (PieceInfo pieceInfo : pieces) {
			adjustPositionForHeight(pieceInfo.getPosition());
		}
		return pieces;
	}

	public List<PieceInfo> getAllTeamOnePieces() {
		return teamOnePieces;
	}

	public List<PieceInfo> getAllTeamTwoPieces() {
		return teamTwoPieces;
	}

	public void setSelectedPiece(IPieceGroup selectedPiece) {
		this.selectedPieceToMove = selectedPiece;
		gameGridModel.setSelectedUserPiece(selectedPiece.getPieceInfo());
	}
}
