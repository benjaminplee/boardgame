package net.todd.games.boardgame;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.vecmath.Point3d;

public class GameEngine implements IGameEngine {
	private final ISceneGenerator sceneGenerator;
	private final IPieceGenerator pieceGenerator;
	private final ICameraGenerator cameraGenerator;
	private final Bounds bounds;
	private final IGameGridFactory gameGridFactory;
	private final IUserPiecesFactory userPiecesFactory;

	public GameEngine(ISceneGenerator sceneGenerator, IPieceGenerator pieceGenerator,
			ICameraGenerator cameraGenerator) {
		this.sceneGenerator = sceneGenerator;
		this.pieceGenerator = pieceGenerator;
		this.cameraGenerator = cameraGenerator;

		bounds = new BoundingSphere(new Point3d(0, 0, 0), 100);
		gameGridFactory = new GameGridFactory();
		userPiecesFactory = new UserPiecesFactory(bounds);
	}

	public void createScene(IPickerFactory pickerFactory) {
		sceneGenerator.lightScene(bounds);
		sceneGenerator.createGameGrid(pickerFactory, gameGridFactory);
		sceneGenerator.createBackground(bounds);
		pieceGenerator.createPieces(pickerFactory, userPiecesFactory);
	}

	public void createCamera(IUniverse su) {
		cameraGenerator.createCamera(su, bounds);
	}
}
