package net.todd.games.boardgame;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Node;

public interface IBranchGroup {
	void compile();

	void addChild(Node node);

	void addChild(IBranchGroup child);

	void removeAllChildren();

	BranchGroup underlyingImplementation();
}
