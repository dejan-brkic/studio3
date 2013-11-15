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

import java.util.LinkedList;
import java.util.UUID;

import org.craftercms.studio.impl.repository.mongodb.domain.Node;
import org.craftercms.studio.impl.repository.mongodb.services.impl.NodeServiceImpl;
import org.craftercms.studio.impl.repository.mongodb.services.impl.PathServicesImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test of PathServicesImpl
 */
public class PathServicesImplTest {


    /**
     * Node Service (the one to be tested)
     */
    private NodeService nodeService;
    /**
     * Path Service Impl
     */
    private PathServicesImpl pathServices;

    @Before
    public void setUp() throws Exception {
        pathServices = new PathServicesImpl();
        nodeService = mock(NodeService.class);
        pathServices.setNodeServiceImpl(nodeService);

    }

    @Test
    public void testGetPathByItem() throws Exception {
        when(nodeService.getNode(Mockito.anyString())).thenReturn(createTree());

        when(nodeService.getNodePath(Mockito.any(Node.class))).then(new Answer<Object>() {
            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                Node n = (Node)invocation.getArguments()[0];
                return "/A/B/C/D";
            }
        });
        String path = pathServices.getPathByItemId("Ticket", "Site", "ItemId");
        String exceptedPath = "/A/B/C/D";
        Assert.assertEquals(exceptedPath, path);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTicketNull() throws Exception {
        pathServices.getPathByItemId(null, "Site", "ItemId");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTicketIsEmpty() throws Exception {
        pathServices.getPathByItemId("", "Site", "ItemId");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTicketIsBlank() throws Exception {
        pathServices.getPathByItemId("   ", "Site", "ItemId");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSiteNull() throws Exception {
        pathServices.getPathByItemId("Ticket", null, "ItemId");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSiteIsEmpty() throws Exception {
        pathServices.getPathByItemId("Ticket", "", "ItemId");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSiteIsBlank() throws Exception {
        pathServices.getPathByItemId("Ticket", " ", "ItemId");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testItemIdtNull() throws Exception {
        pathServices.getPathByItemId("Ticket", "Site", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testItemIdIsEmpty() throws Exception {
        pathServices.getPathByItemId("Ticket", "Site", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testItemIdIsBlank() throws Exception {
        pathServices.getPathByItemId("Ticket", "Site", "  ");
    }

    private Node createTree() {
        Node root = new Node();
        root.getCore().setNodeName("/");
        root.setId(UUID.randomUUID().toString());
        Node a = new Node();
        a.getCore().setNodeName("A");
        a.setId(UUID.randomUUID().toString());
        Node b = new Node();
        b.getCore().setNodeName("B");
        b.setId(UUID.randomUUID().toString());
        Node c = new Node();
        c.getCore().setNodeName("C");
        c.setId(UUID.randomUUID().toString());
        Node d = new Node();
        d.getCore().setNodeName("D");
        d.setId(UUID.randomUUID().toString());
        LinkedList<String> ancestoers = new LinkedList<String>();
        ancestoers.add(root.getId());
        ancestoers.add(a.getId());
        ancestoers.add(b.getId());
        ancestoers.add(c.getId());
        d.setAncestors(ancestoers);
        return d;
    }
}
