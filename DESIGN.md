TEMPORAL
========

## Camera
- Fixed camera, small arena size (fits entirely in camera viewport), enemies spawn from edges
- Movable camera, large arena size, enemies spawn randomly in entire region

## Collisions
- Collision with enemy -> destroy enemy, deal self-damage, slow down significantly
- Self-damage proportional to enemy health
- Short invincibility (noclip with others) for short time(0.5 seconds)

## Weapons
- If you get hit, you suck.
- Destroy enemy, deal damage

## Player
- Shooting
- Move
- Turn (mouse)
- Momentum (small to add fluid feel)
- Health bar (decent amounts)
- ALL THE BULLETS

## Enemies
- Slammers
  - Asteroids (fixed direction, bounce off sides)
  - Tracking (seek) 
  - (TODO): Charge (turn, lock & fixed direction)
- Shooters
  - Turrets (medium, fixed, high health, low fire rate, high bullet damage)
  - (TODO): Fighters (small, agile, low health, high fire rate, low bullet damage)

## Time
- Tie time to current velocity (inverse relation)

## Edges
- Stop at edges

Extras if we have time
----------------------
- Weapon types
- Powerups
- Boss fights
- Shields (regenerating)
- Nuke/Smartbomb (Destroy all enemies on screen)
