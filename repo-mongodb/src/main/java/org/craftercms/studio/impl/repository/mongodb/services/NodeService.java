/*
 * Copyright (C) 2007-2013 Crafter Software Corporation.
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.craftercms.studio.impl.repository.mongodb.services;

import java.io.InputStream;
import java.util.List;

import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.impl.repository.mongodb.domain.Node;

/**
 * Node Services in Definition.
 * <br/>
 * <b>This class is intended to be use internally only
 * usage outside this artifact could break or corrupt the repository</b>
 */
public interface NodeService {
    /**
     * Node Collection name.
     */
    final String NODES_COLLECTION = "nodes";

    /**
     * Creates a File Node.
     * This also validated that the parent node is not null
     * and Folder Type.
     *
     * @param parent      Parent Node Must be of Folder Type.
     * @param fileName    File folderName
     * @param fileLabel   Label of the File.
     * @param creatorName Creator name (who create this)
     * @param content     content of this node
     * @return A new File Node.
     * @throws IllegalArgumentException                                <ul>
     *                                                                 <li>If parent is
     *                                                                 null</li>
     *                                                                 <li>If parent is not
     *                                                                 a Folder (Node of
     *                                                                 type Folder)</li>
     *                                                                 </ul>
     * @throws org.craftercms.studio.commons.exception.StudioException If system was unable
     *                                                                 to store de node on the repository.
     */
    Node createFileNode(Node parent, String fileName, String fileLabel, String creatorName,
                        InputStream content) throws StudioException;

    /**
     * Create a new Folder TypeNode.
     *
     * @param parent      Parent Node Must be of Folder Type.
     * @param folderName  File folderName
     * @param creatorName Creator name (who create this)
     * @return a new Folder Node.
     * @throws IllegalArgumentException                                <ul>
     *                                                                 <li>If parent is
     *                                                                 null</li>
     *                                                                 <li>If parent is not
     *                                                                 a Folder (Node of
     *                                                                 type Folder)</li>
     *                                                                 <li>If metadata is
     *                                                                 incompatible with a
     *                                                                 Folder Node</li>
     *                                                                 </ul>
     * @throws org.craftercms.studio.commons.exception.StudioException If system was unable
     *                                                                 to store de node on the repository.
     */
    Node createFolderNode(Node parent, String folderName, String folderLabel,
                          String creatorName) throws StudioException;

    /**
     * Finds all nodes for a given parent.
     * Sending parent as null is equivalent to call getRootNode()
     *
     * @param parent parent to look for
     * @return Empty List if given node has no children.
     * List of all nodes that are children of the given node.
     */
    Iterable<Node> findNodesByParents(List<Node> parent) throws StudioException;

    Iterable<Node> findNodeByParent(Node node) throws StudioException;

    Iterable<Node> findNodeChildren(Node node) throws StudioException;

    /**
     * Gets the Root node.
     *
     * @return the Root node , <b>Never Null</b>
     */
    Node getRootNode() throws StudioException;

    /**
     * Checks if the node is a Folder.
     *
     * @param nodeToCheck node to check
     * @return True if the node is a Folder, false otherwise.
     */
    boolean isNodeFolder(Node nodeToCheck);

    /**
     * Checks if the node is a File.
     *
     * @param nodeToCheck node to check
     * @return True if the node is a File, false otherwise.
     */
    boolean isNodeFile(Node nodeToCheck);

    /**
     * Finds a node using it's Id.
     *
     * @param nodeId Id of the node
     * @return The node with the given ID, null if not found.
     * @throws IllegalArgumentException                                If nodeId is null, empty or blank
     * @throws org.craftercms.studio.commons.exception.StudioException If is unable to find.
     */
    Node getNode(String nodeId) throws StudioException;

    /**
     * Finds a node by the ancestors and the node name.
     *
     * @param ancestors List of ancestors
     * @param nodeName  Name of the node looking for.
     * @return The node with given ancenstors and given name. Null if nothing is found.
     */
    Node findNodeByAncestorsAndName(List<String> ancestors, String nodeName) throws StudioException;

    Node findNodeByAncestorsIdsAndName(List<String> ancestors, String nodeName) throws StudioException;

    /**
     * Gets the site Root node, null if the site node does not exist (site haven't been created or deleted).
     *
     * @param siteName Site name.
     * @return the node that represents the site root, null if not found.
     */
    Node getSiteNode(String siteName) throws StudioException;

    /**
     * Creates a folder tree base on the given path, starts from the last know leaf.
     *
     * @param path    Path to create.
     * @param creator Creator of this folder.
     * @return Node from representing the last leaf.
     */
    Node createFolderStructure(String path, final String creator) throws StudioException;

    /**
     * Search and returns a InputStream for a file with the given Id.
     *
     * @param fileId File id to be found.
     * @return A inputSteam for the file with the given Id.<br/>
     * Null if nothing is found with that id.
     * @throws java.lang.IllegalArgumentException                      If file id is not
     *                                                                 valid (null,empty or whitespace)
     * @throws org.craftercms.studio.commons.exception.StudioException if unable to search
     *                                                                 or retrieve the
     *                                                                 InputStream.
     */
    InputStream getFile(String fileId) throws StudioException;

    /**
     * Gets all the children nodes for the given node.<br/>
     * (Children  are nodes which ancestors are the same of the  given nodeId + the given Node)
     *
     * @param nodeId Node id of the parent
     * @return A List of nodes that are children of
     */
    Iterable<Node> getChildren(String nodeId) throws StudioException;

    String getNodePath(Node node) throws StudioException;

    /**
     * Counts how many Root nodes they are.
     * if there is more that 1, throw a Execution
     */
    void countRootNodes() throws StudioException;

    void updateFileNode(String itemId, InputStream content) throws StudioException;

    void deleteFileNode(String itemId) throws StudioException;
}
