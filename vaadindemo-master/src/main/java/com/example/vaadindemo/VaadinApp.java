package com.example.vaadindemo;

import java.awt.Component;

import javax.swing.JOptionPane;

import com.example.vaadindemo.domain.Player;
import com.example.vaadindemo.domain.Team;
import com.example.vaadindemo.service.PlayerManager;
import com.example.vaadindemo.service.TeamManager;
import com.vaadin.annotations.Title;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@Title("Football Players App")
public class VaadinApp extends UI {

	private static final long serialVersionUID = 1L;
	
	private PlayerManager PlayerManager = new PlayerManager();	
	private Player player = new Player("","", null,"");
	private Team team = new Team("","",0000,"" );
	
	private BeanItem<Player> playerItem = new BeanItem<Player>(player);
	private BeanItem<Team> teamItem = new BeanItem<Team>(team);
				
	private TeamManager TeamManager = new TeamManager();
	
	private BeanItemContainer<Player> players = new BeanItemContainer<Player>(Player.class);
	private BeanItemContainer<Team> teams = new BeanItemContainer<Team>(Team.class);
	
	Component frame;
	
	private ListSelect teamlist = new ListSelect("Team");
	private ListSelect leaguelist = new ListSelect("League");
	public DateField date = new DateField("Date");
	private Exception exception = null;
	
	enum Action{
		EDIT, ADD;
	}
	
	
	private class PlayerWindow extends Window {
		
		private static final long serialVersionUID = 1L;
		 private Action action;
		 
		 	public PlayerWindow(Action act){
		 		this.action = act;
		 		
		 		setModal(true);
		 		center();
		 		
		 		switch(action){
		 			case ADD:
		 				setCaption("Add new player");
		 				break;
		 			case EDIT:
		 				setCaption("Edit a player");
		 				break;
		 			default:
		 				break;
		 		}
		 		
		 		final FormLayout form = new FormLayout();
		 		final FieldGroup binder = new FieldGroup(playerItem);
		 		
		 		final Button saveBtn = new Button("Save");
		 		final Button cancelBtn = new Button("Cancel");
		 		
		 		form.addComponent(binder.buildAndBind("First Name", "firstname"));
		 		form.addComponent(binder.buildAndBind("Last Name", "lastname"));
		 		form.addComponent(date);
		 		form.addComponent(teamlist);
		 				 		
		 		binder.setBuffered(true);
		 		
		 		binder.getField("firstname").setRequired(true);
		 		binder.getField("lastname").setRequired(true);
		 		
		 		VerticalLayout fvl = new VerticalLayout();
		 		fvl.setMargin(true);
		 		fvl.addComponent(form);
		 		
		 		HorizontalLayout hl = new HorizontalLayout();
		 		hl.addComponent(saveBtn);
		 		hl.addComponent(cancelBtn);
		 		fvl.addComponent(hl);
		 		
		 		setContent(fvl);
		 			 		
		 		saveBtn.addClickListener(new ClickListener() {

					private static final long serialVersionUID = 1L;

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							
							binder.commit();
							
							if (action == Action.ADD) {
								player.setYear(date.getValue());
								player.setTeam(teamlist.getValue().toString());
								PlayerManager.addPlayer(player);
							} else if (action == Action.EDIT) {		
							
								player.setYear(date.getValue());
								player.setTeam(teamlist.getValue().toString());
								
								try {
									PlayerManager.update(player);
								} catch (Exception e) {
									JOptionPane.showMessageDialog(frame, "You can only edit player's team");
								}
							}

							players.removeAllItems();
							players.addAll(PlayerManager.findAll());
							close();
						} catch (CommitException e) {
							e.printStackTrace();
						}
					}
				});

				cancelBtn.addClickListener(new ClickListener() {

					private static final long serialVersionUID = 1L;

					@Override
					public void buttonClick(ClickEvent event) {
						binder.discard();
						close();
					}
				});
		 		
		 	}
	}
	
	private class TeamWindow extends Window {
		
		private static final long serialVersionUID = 1L;
		 private Action action;
		 
		 	public TeamWindow(Action act){
		 		this.action = act;
		 		
		 		setModal(true);
		 		center();
		 		
		 		switch(action){
		 			case ADD:
		 				setCaption("Add new team");
		 				break;
		 			default:
		 				break;
		 		}
		 		
		 		final FormLayout form = new FormLayout();
		 		final FieldGroup binder = new FieldGroup(teamItem);
		 		
		 		final Button saveBtn = new Button("Save");
		 		final Button cancelBtn = new Button("Cancel");
		 		
		 		form.addComponent(binder.buildAndBind("Name", "name"));
		 		form.addComponent(binder.buildAndBind("Coach", "coach"));
		 		form.addComponent(binder.buildAndBind("Year", "year"));
		 		form.addComponent(leaguelist);
		 				 		
		 		binder.setBuffered(true);
		 		
		 		binder.getField("name").setRequired(true);
		 		binder.getField("coach").setRequired(true);
		 		binder.getField("year").setRequired(true);	 		
		 		
		 		VerticalLayout fvl = new VerticalLayout();
		 		fvl.setMargin(true);
		 		fvl.addComponent(form);
		 		
		 		HorizontalLayout hl = new HorizontalLayout();
		 		hl.addComponent(saveBtn);
		 		hl.addComponent(cancelBtn);
		 		fvl.addComponent(hl);
		 		
		 		setContent(fvl);
		 			 		
		 		saveBtn.addClickListener(new ClickListener() {

					private static final long serialVersionUID = 1L;

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							binder.commit();

							if (action == Action.ADD) {
								team.setLeague(leaguelist.getValue().toString());
								teamlist.addItem(binder.getField("name"));
								TeamManager.addTeam(team);
							}

							teams.removeAllItems();
							teams.addAll(TeamManager.findAll());
							close();
						} catch (CommitException e) {
							e.printStackTrace();
						}
					}
				});

				cancelBtn.addClickListener(new ClickListener() {

					private static final long serialVersionUID = 1L;

					@Override
					public void buttonClick(ClickEvent event) {
						binder.discard();
						close();
					}
				});
		 		
		 	}
	}
				
	@Override
	protected void init(VaadinRequest request) {
		
		teamlist.addItems("Retired","Non-attached");
		leaguelist.addItems("Bundesliga","Eredivisie","Ekstraklasa");
		
		teamlist.setRows(1);
		leaguelist.setRows(1);
		
//------------------------------------------------------------------------------------------------------
			
		Button addPlayerFormBtn = new Button("Add player");
		Button deletePlayerFormBtn = new Button("Delete player");
		Button editPlayerFormBtn = new Button("Edit player");
		
		VerticalLayout vl = new VerticalLayout();
		setContent(vl);

		addPlayerFormBtn.addClickListener(new ClickListener() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				addWindow(new PlayerWindow(Action.ADD));
			}
		});
		
		editPlayerFormBtn.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				addWindow(new PlayerWindow(Action.EDIT));
			}
		});

		deletePlayerFormBtn.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (!player.getFirstname().isEmpty()) {
					PlayerManager.delete(player);
					players.removeAllItems();
					players.addAll(PlayerManager.findAll());
				}
			}
		});
		
		HorizontalLayout hl = new HorizontalLayout();

		final Table playersTable = new Table("", players);
		playersTable.setColumnHeader("firstName", "Name");
		playersTable.setColumnHeader("lastName", "Surname");
		playersTable.setColumnHeader("year", "Birth");
		playersTable.setColumnHeader("team", "Team");

		playersTable.setSelectable(true);
		playersTable.setImmediate(true);
		
		playersTable.addValueChangeListener(new Property.ValueChangeListener() {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				Player selectedPlayer = (Player) playersTable.getValue();
				if (selectedPlayer == null) {
					player.setFirstname("");
					player.setLastname("");
					player.setYear(null);
					player.setTeam("");
				} else {
					player.setFirstname(selectedPlayer.getFirstname());
					player.setLastname(selectedPlayer.getLastname());
					player.setYear(selectedPlayer.getYear());
					player.setTeam(selectedPlayer.getTeam());
				}				
			}
		});
		
		hl.addComponent(addPlayerFormBtn);
		hl.addComponent(editPlayerFormBtn);
		hl.addComponent(deletePlayerFormBtn);
		
		playersTable.setWidth("600px");
		playersTable.setColumnWidth("firstname", 100);
		playersTable.setColumnWidth("lastname", 150);
		playersTable.setColumnWidth("Team", 290);
		playersTable.setColumnWidth("year", 60);
		hl.setHeight("50px");
		
				
		vl.addComponent(playersTable);
		vl.addComponent(hl);
		
		vl.setComponentAlignment(playersTable, Alignment.TOP_CENTER);
		vl.setComponentAlignment(hl, Alignment.TOP_CENTER);

		
//------------------------------------------------------------------------------------------------------------------------------
				
		final Table teamsTable = new Table("", teams);
		teamsTable.setColumnHeader("name", "Name");
		teamsTable.setColumnHeader("coach", "Coach Name");
		teamsTable.setColumnHeader("year", "Year");
		
		teamsTable.setVisibleColumns(new Object[] {"name", "coach", "year", "league"});

		teamsTable.setSelectable(true);
		teamsTable.setImmediate(true);
		teamsTable.setWidth("500px");
		teamsTable.setColumnWidth("year", 60);
		
		vl.addComponent(teamsTable);
		vl.setComponentAlignment(teamsTable, Alignment.TOP_CENTER);
		
		Button addTeamFormBtn = new Button("Add team");
		
		HorizontalLayout hl2 = new HorizontalLayout();
		
		hl2.addComponent(addTeamFormBtn);
		
		vl.addComponent(hl2);
		vl.setComponentAlignment(hl2, Alignment.TOP_CENTER);
		
		addTeamFormBtn.addClickListener(new ClickListener() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				addWindow(new TeamWindow(Action.ADD));
			}
		});
		
		setContent(vl);
		
	}

	

}
