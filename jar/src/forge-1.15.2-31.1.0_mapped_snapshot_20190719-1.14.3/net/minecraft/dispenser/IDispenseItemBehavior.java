package net.minecraft.dispenser;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.SkullBlock;
import net.minecraft.block.TNTBlock;
import net.minecraft.block.WitherSkeletonSkullBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.ExperienceBottleEntity;
import net.minecraft.entity.item.FireworkRocketEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.EggEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.entity.projectile.SpectralArrowEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.BeehiveTileEntity;
import net.minecraft.tileentity.DispenserTileEntity;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

public interface IDispenseItemBehavior {
   IDispenseItemBehavior NOOP = (p_lambda$static$0_0_, p_lambda$static$0_1_) -> {
      return p_lambda$static$0_1_;
   };

   ItemStack dispense(IBlockSource var1, ItemStack var2);

   static void init() {
      DispenserBlock.registerDispenseBehavior(Items.ARROW, new ProjectileDispenseBehavior() {
         protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack p_82499_3_) {
            ArrowEntity arrowentity = new ArrowEntity(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            arrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.ALLOWED;
            return arrowentity;
         }
      });
      DispenserBlock.registerDispenseBehavior(Items.TIPPED_ARROW, new ProjectileDispenseBehavior() {
         protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack p_82499_3_) {
            ArrowEntity arrowentity = new ArrowEntity(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            arrowentity.setPotionEffect(p_82499_3_);
            arrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.ALLOWED;
            return arrowentity;
         }
      });
      DispenserBlock.registerDispenseBehavior(Items.SPECTRAL_ARROW, new ProjectileDispenseBehavior() {
         protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack p_82499_3_) {
            AbstractArrowEntity abstractarrowentity = new SpectralArrowEntity(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            abstractarrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.ALLOWED;
            return abstractarrowentity;
         }
      });
      DispenserBlock.registerDispenseBehavior(Items.EGG, new ProjectileDispenseBehavior() {
         protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack p_82499_3_) {
            return (IProjectile)Util.make(new EggEntity(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ()), (p_lambda$getProjectileEntity$0_1_) -> {
               p_lambda$getProjectileEntity$0_1_.func_213884_b(p_82499_3_);
            });
         }
      });
      DispenserBlock.registerDispenseBehavior(Items.SNOWBALL, new ProjectileDispenseBehavior() {
         protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack p_82499_3_) {
            return (IProjectile)Util.make(new SnowballEntity(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ()), (p_lambda$getProjectileEntity$0_1_) -> {
               p_lambda$getProjectileEntity$0_1_.func_213884_b(p_82499_3_);
            });
         }
      });
      DispenserBlock.registerDispenseBehavior(Items.EXPERIENCE_BOTTLE, new ProjectileDispenseBehavior() {
         protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack p_82499_3_) {
            return (IProjectile)Util.make(new ExperienceBottleEntity(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ()), (p_lambda$getProjectileEntity$0_1_) -> {
               p_lambda$getProjectileEntity$0_1_.func_213884_b(p_82499_3_);
            });
         }

         protected float getProjectileInaccuracy() {
            return super.getProjectileInaccuracy() * 0.5F;
         }

         protected float getProjectileVelocity() {
            return super.getProjectileVelocity() * 1.25F;
         }
      });
      DispenserBlock.registerDispenseBehavior(Items.SPLASH_POTION, new IDispenseItemBehavior() {
         public ItemStack dispense(IBlockSource p_dispense_1_, ItemStack p_dispense_2_) {
            return (new ProjectileDispenseBehavior() {
               protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack p_82499_3_) {
                  return (IProjectile)Util.make(new PotionEntity(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ()), (p_lambda$getProjectileEntity$0_1_) -> {
                     p_lambda$getProjectileEntity$0_1_.setItem(p_82499_3_);
                  });
               }

               protected float getProjectileInaccuracy() {
                  return super.getProjectileInaccuracy() * 0.5F;
               }

               protected float getProjectileVelocity() {
                  return super.getProjectileVelocity() * 1.25F;
               }
            }).dispense(p_dispense_1_, p_dispense_2_);
         }
      });
      DispenserBlock.registerDispenseBehavior(Items.LINGERING_POTION, new IDispenseItemBehavior() {
         public ItemStack dispense(IBlockSource p_dispense_1_, ItemStack p_dispense_2_) {
            return (new ProjectileDispenseBehavior() {
               protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack p_82499_3_) {
                  return (IProjectile)Util.make(new PotionEntity(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ()), (p_lambda$getProjectileEntity$0_1_) -> {
                     p_lambda$getProjectileEntity$0_1_.setItem(p_82499_3_);
                  });
               }

               protected float getProjectileInaccuracy() {
                  return super.getProjectileInaccuracy() * 0.5F;
               }

               protected float getProjectileVelocity() {
                  return super.getProjectileVelocity() * 1.25F;
               }
            }).dispense(p_dispense_1_, p_dispense_2_);
         }
      });
      DefaultDispenseItemBehavior defaultdispenseitembehavior = new DefaultDispenseItemBehavior() {
         public ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
            Direction direction = (Direction)p_82487_1_.getBlockState().get(DispenserBlock.FACING);
            EntityType<?> entitytype = ((SpawnEggItem)p_82487_2_.getItem()).getType(p_82487_2_.getTag());
            entitytype.spawn(p_82487_1_.getWorld(), p_82487_2_, (PlayerEntity)null, p_82487_1_.getBlockPos().offset(direction), SpawnReason.DISPENSER, direction != Direction.UP, false);
            p_82487_2_.shrink(1);
            return p_82487_2_;
         }
      };
      Iterator var1 = SpawnEggItem.getEggs().iterator();

      while(var1.hasNext()) {
         SpawnEggItem spawneggitem = (SpawnEggItem)var1.next();
         DispenserBlock.registerDispenseBehavior(spawneggitem, defaultdispenseitembehavior);
      }

      DispenserBlock.registerDispenseBehavior(Items.ARMOR_STAND, new DefaultDispenseItemBehavior() {
         public ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
            Direction direction = (Direction)p_82487_1_.getBlockState().get(DispenserBlock.FACING);
            BlockPos blockpos = p_82487_1_.getBlockPos().offset(direction);
            World world = p_82487_1_.getWorld();
            ArmorStandEntity armorstandentity = new ArmorStandEntity(world, (double)blockpos.getX() + 0.5D, (double)blockpos.getY(), (double)blockpos.getZ() + 0.5D);
            EntityType.applyItemNBT(world, (PlayerEntity)null, armorstandentity, p_82487_2_.getTag());
            armorstandentity.rotationYaw = direction.getHorizontalAngle();
            world.addEntity(armorstandentity);
            p_82487_2_.shrink(1);
            return p_82487_2_;
         }
      });
      DispenserBlock.registerDispenseBehavior(Items.FIREWORK_ROCKET, new DefaultDispenseItemBehavior() {
         public ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
            Direction direction = (Direction)p_82487_1_.getBlockState().get(DispenserBlock.FACING);
            double d0 = (double)direction.getXOffset();
            double d1 = (double)direction.getYOffset();
            double d2 = (double)direction.getZOffset();
            double d3 = p_82487_1_.getX() + d0;
            double d4 = (double)((float)p_82487_1_.getBlockPos().getY() + 0.2F);
            double d5 = p_82487_1_.getZ() + d2;
            FireworkRocketEntity fireworkrocketentity = new FireworkRocketEntity(p_82487_1_.getWorld(), p_82487_2_, d3, d4, d5, true);
            fireworkrocketentity.shoot(d0, d1, d2, 0.5F, 1.0F);
            p_82487_1_.getWorld().addEntity(fireworkrocketentity);
            p_82487_2_.shrink(1);
            return p_82487_2_;
         }

         protected void playDispenseSound(IBlockSource p_82485_1_) {
            p_82485_1_.getWorld().playEvent(1004, p_82485_1_.getBlockPos(), 0);
         }
      });
      DispenserBlock.registerDispenseBehavior(Items.FIRE_CHARGE, new DefaultDispenseItemBehavior() {
         public ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
            Direction direction = (Direction)p_82487_1_.getBlockState().get(DispenserBlock.FACING);
            IPosition iposition = DispenserBlock.getDispensePosition(p_82487_1_);
            double d0 = iposition.getX() + (double)((float)direction.getXOffset() * 0.3F);
            double d1 = iposition.getY() + (double)((float)direction.getYOffset() * 0.3F);
            double d2 = iposition.getZ() + (double)((float)direction.getZOffset() * 0.3F);
            World world = p_82487_1_.getWorld();
            Random random = world.rand;
            double d3 = random.nextGaussian() * 0.05D + (double)direction.getXOffset();
            double d4 = random.nextGaussian() * 0.05D + (double)direction.getYOffset();
            double d5 = random.nextGaussian() * 0.05D + (double)direction.getZOffset();
            world.addEntity((Entity)Util.make(new SmallFireballEntity(world, d0, d1, d2, d3, d4, d5), (p_lambda$dispenseStack$0_1_) -> {
               p_lambda$dispenseStack$0_1_.func_213898_b(p_82487_2_);
            }));
            p_82487_2_.shrink(1);
            return p_82487_2_;
         }

         protected void playDispenseSound(IBlockSource p_82485_1_) {
            p_82485_1_.getWorld().playEvent(1018, p_82485_1_.getBlockPos(), 0);
         }
      });
      DispenserBlock.registerDispenseBehavior(Items.OAK_BOAT, new DispenseBoatBehavior(BoatEntity.Type.OAK));
      DispenserBlock.registerDispenseBehavior(Items.SPRUCE_BOAT, new DispenseBoatBehavior(BoatEntity.Type.SPRUCE));
      DispenserBlock.registerDispenseBehavior(Items.BIRCH_BOAT, new DispenseBoatBehavior(BoatEntity.Type.BIRCH));
      DispenserBlock.registerDispenseBehavior(Items.JUNGLE_BOAT, new DispenseBoatBehavior(BoatEntity.Type.JUNGLE));
      DispenserBlock.registerDispenseBehavior(Items.DARK_OAK_BOAT, new DispenseBoatBehavior(BoatEntity.Type.DARK_OAK));
      DispenserBlock.registerDispenseBehavior(Items.ACACIA_BOAT, new DispenseBoatBehavior(BoatEntity.Type.ACACIA));
      IDispenseItemBehavior idispenseitembehavior = new DefaultDispenseItemBehavior() {
         private final DefaultDispenseItemBehavior field_218406_b = new DefaultDispenseItemBehavior();

         public ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
            BucketItem bucketitem = (BucketItem)p_82487_2_.getItem();
            BlockPos blockpos = p_82487_1_.getBlockPos().offset((Direction)p_82487_1_.getBlockState().get(DispenserBlock.FACING));
            World world = p_82487_1_.getWorld();
            if (bucketitem.tryPlaceContainedLiquid((PlayerEntity)null, world, blockpos, (BlockRayTraceResult)null)) {
               bucketitem.onLiquidPlaced(world, p_82487_2_, blockpos);
               return new ItemStack(Items.BUCKET);
            } else {
               return this.field_218406_b.dispense(p_82487_1_, p_82487_2_);
            }
         }
      };
      DispenserBlock.registerDispenseBehavior(Items.LAVA_BUCKET, idispenseitembehavior);
      DispenserBlock.registerDispenseBehavior(Items.WATER_BUCKET, idispenseitembehavior);
      DispenserBlock.registerDispenseBehavior(Items.SALMON_BUCKET, idispenseitembehavior);
      DispenserBlock.registerDispenseBehavior(Items.COD_BUCKET, idispenseitembehavior);
      DispenserBlock.registerDispenseBehavior(Items.PUFFERFISH_BUCKET, idispenseitembehavior);
      DispenserBlock.registerDispenseBehavior(Items.TROPICAL_FISH_BUCKET, idispenseitembehavior);
      DispenserBlock.registerDispenseBehavior(Items.BUCKET, new DefaultDispenseItemBehavior() {
         private final DefaultDispenseItemBehavior field_229426_b_ = new DefaultDispenseItemBehavior();

         public ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
            IWorld iworld = p_82487_1_.getWorld();
            BlockPos blockpos = p_82487_1_.getBlockPos().offset((Direction)p_82487_1_.getBlockState().get(DispenserBlock.FACING));
            BlockState blockstate = iworld.getBlockState(blockpos);
            Block block = blockstate.getBlock();
            if (block instanceof IBucketPickupHandler) {
               Fluid fluid = ((IBucketPickupHandler)block).pickupFluid(iworld, blockpos, blockstate);
               if (!(fluid instanceof FlowingFluid)) {
                  return super.dispenseStack(p_82487_1_, p_82487_2_);
               } else {
                  Item item = fluid.getFilledBucket();
                  p_82487_2_.shrink(1);
                  if (p_82487_2_.isEmpty()) {
                     return new ItemStack(item);
                  } else {
                     if (((DispenserTileEntity)p_82487_1_.getBlockTileEntity()).addItemStack(new ItemStack(item)) < 0) {
                        this.field_229426_b_.dispense(p_82487_1_, new ItemStack(item));
                     }

                     return p_82487_2_;
                  }
               }
            } else {
               return super.dispenseStack(p_82487_1_, p_82487_2_);
            }
         }
      });
      DispenserBlock.registerDispenseBehavior(Items.FLINT_AND_STEEL, new OptionalDispenseBehavior() {
         protected ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
            World world = p_82487_1_.getWorld();
            this.successful = true;
            BlockPos blockpos = p_82487_1_.getBlockPos().offset((Direction)p_82487_1_.getBlockState().get(DispenserBlock.FACING));
            BlockState blockstate = world.getBlockState(blockpos);
            if (FlintAndSteelItem.func_219996_a(blockstate, world, blockpos)) {
               world.setBlockState(blockpos, Blocks.FIRE.getDefaultState());
            } else if (FlintAndSteelItem.isUnlitCampfire(blockstate)) {
               world.setBlockState(blockpos, (BlockState)blockstate.with(BlockStateProperties.LIT, true));
            } else if (blockstate.isFlammable(world, blockpos, ((Direction)p_82487_1_.getBlockState().get(DispenserBlock.FACING)).getOpposite())) {
               blockstate.catchFire(world, blockpos, ((Direction)p_82487_1_.getBlockState().get(DispenserBlock.FACING)).getOpposite(), (LivingEntity)null);
               if (blockstate.getBlock() instanceof TNTBlock) {
                  world.removeBlock(blockpos, false);
               }
            } else {
               this.successful = false;
            }

            if (this.successful && p_82487_2_.attemptDamageItem(1, world.rand, (ServerPlayerEntity)null)) {
               p_82487_2_.setCount(0);
            }

            return p_82487_2_;
         }
      });
      DispenserBlock.registerDispenseBehavior(Items.BONE_MEAL, new OptionalDispenseBehavior() {
         protected ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
            this.successful = true;
            World world = p_82487_1_.getWorld();
            BlockPos blockpos = p_82487_1_.getBlockPos().offset((Direction)p_82487_1_.getBlockState().get(DispenserBlock.FACING));
            if (!BoneMealItem.applyBonemeal(p_82487_2_, world, blockpos) && !BoneMealItem.growSeagrass(p_82487_2_, world, blockpos, (Direction)null)) {
               this.successful = false;
            } else if (!world.isRemote) {
               world.playEvent(2005, blockpos, 0);
            }

            return p_82487_2_;
         }
      });
      DispenserBlock.registerDispenseBehavior(Blocks.TNT, new DefaultDispenseItemBehavior() {
         protected ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
            World world = p_82487_1_.getWorld();
            BlockPos blockpos = p_82487_1_.getBlockPos().offset((Direction)p_82487_1_.getBlockState().get(DispenserBlock.FACING));
            TNTEntity tntentity = new TNTEntity(world, (double)blockpos.getX() + 0.5D, (double)blockpos.getY(), (double)blockpos.getZ() + 0.5D, (LivingEntity)null);
            world.addEntity(tntentity);
            world.playSound((PlayerEntity)null, tntentity.func_226277_ct_(), tntentity.func_226278_cu_(), tntentity.func_226281_cx_(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
            p_82487_2_.shrink(1);
            return p_82487_2_;
         }
      });
      IDispenseItemBehavior idispenseitembehavior1 = new OptionalDispenseBehavior() {
         protected ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
            this.successful = ArmorItem.func_226626_a_(p_82487_1_, p_82487_2_);
            return p_82487_2_;
         }
      };
      DispenserBlock.registerDispenseBehavior(Items.CREEPER_HEAD, idispenseitembehavior1);
      DispenserBlock.registerDispenseBehavior(Items.ZOMBIE_HEAD, idispenseitembehavior1);
      DispenserBlock.registerDispenseBehavior(Items.DRAGON_HEAD, idispenseitembehavior1);
      DispenserBlock.registerDispenseBehavior(Items.SKELETON_SKULL, idispenseitembehavior1);
      DispenserBlock.registerDispenseBehavior(Items.PLAYER_HEAD, idispenseitembehavior1);
      DispenserBlock.registerDispenseBehavior(Items.WITHER_SKELETON_SKULL, new OptionalDispenseBehavior() {
         protected ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
            World world = p_82487_1_.getWorld();
            Direction direction = (Direction)p_82487_1_.getBlockState().get(DispenserBlock.FACING);
            BlockPos blockpos = p_82487_1_.getBlockPos().offset(direction);
            if (world.isAirBlock(blockpos) && WitherSkeletonSkullBlock.canSpawnMob(world, blockpos, p_82487_2_)) {
               world.setBlockState(blockpos, (BlockState)Blocks.WITHER_SKELETON_SKULL.getDefaultState().with(SkullBlock.ROTATION, direction.getAxis() == Direction.Axis.Y ? 0 : direction.getOpposite().getHorizontalIndex() * 4), 3);
               TileEntity tileentity = world.getTileEntity(blockpos);
               if (tileentity instanceof SkullTileEntity) {
                  WitherSkeletonSkullBlock.checkWitherSpawn(world, blockpos, (SkullTileEntity)tileentity);
               }

               p_82487_2_.shrink(1);
               this.successful = true;
            } else {
               this.successful = ArmorItem.func_226626_a_(p_82487_1_, p_82487_2_);
            }

            return p_82487_2_;
         }
      });
      DispenserBlock.registerDispenseBehavior(Blocks.CARVED_PUMPKIN, new OptionalDispenseBehavior() {
         protected ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
            World world = p_82487_1_.getWorld();
            BlockPos blockpos = p_82487_1_.getBlockPos().offset((Direction)p_82487_1_.getBlockState().get(DispenserBlock.FACING));
            CarvedPumpkinBlock carvedpumpkinblock = (CarvedPumpkinBlock)Blocks.CARVED_PUMPKIN;
            if (world.isAirBlock(blockpos) && carvedpumpkinblock.canDispenserPlace(world, blockpos)) {
               if (!world.isRemote) {
                  world.setBlockState(blockpos, carvedpumpkinblock.getDefaultState(), 3);
               }

               p_82487_2_.shrink(1);
               this.successful = true;
            } else {
               this.successful = ArmorItem.func_226626_a_(p_82487_1_, p_82487_2_);
            }

            return p_82487_2_;
         }
      });
      DispenserBlock.registerDispenseBehavior(Blocks.SHULKER_BOX.asItem(), new ShulkerBoxDispenseBehavior());
      DyeColor[] var3 = DyeColor.values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         DyeColor dyecolor = var3[var5];
         DispenserBlock.registerDispenseBehavior(ShulkerBoxBlock.getBlockByColor(dyecolor).asItem(), new ShulkerBoxDispenseBehavior());
      }

      DispenserBlock.registerDispenseBehavior(Items.GLASS_BOTTLE.asItem(), new OptionalDispenseBehavior() {
         private final DefaultDispenseItemBehavior field_229423_c_ = new DefaultDispenseItemBehavior();

         private ItemStack func_229424_a_(IBlockSource p_229424_1_, ItemStack p_229424_2_, ItemStack p_229424_3_) {
            p_229424_2_.shrink(1);
            if (p_229424_2_.isEmpty()) {
               return p_229424_3_.copy();
            } else {
               if (((DispenserTileEntity)p_229424_1_.getBlockTileEntity()).addItemStack(p_229424_3_.copy()) < 0) {
                  this.field_229423_c_.dispense(p_229424_1_, p_229424_3_.copy());
               }

               return p_229424_2_;
            }
         }

         public ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
            this.successful = false;
            IWorld iworld = p_82487_1_.getWorld();
            BlockPos blockpos = p_82487_1_.getBlockPos().offset((Direction)p_82487_1_.getBlockState().get(DispenserBlock.FACING));
            BlockState blockstate = iworld.getBlockState(blockpos);
            Block block = blockstate.getBlock();
            if (block.isIn(BlockTags.field_226151_aa_) && (Integer)blockstate.get(BeehiveBlock.field_226873_c_) >= 5) {
               ((BeehiveBlock)blockstate.getBlock()).func_226877_a_(iworld.getWorld(), blockstate, blockpos, (PlayerEntity)null, BeehiveTileEntity.State.BEE_RELEASED);
               this.successful = true;
               return this.func_229424_a_(p_82487_1_, p_82487_2_, new ItemStack(Items.field_226638_pX_));
            } else if (iworld.getFluidState(blockpos).isTagged(FluidTags.WATER)) {
               this.successful = true;
               return this.func_229424_a_(p_82487_1_, p_82487_2_, PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER));
            } else {
               return super.dispenseStack(p_82487_1_, p_82487_2_);
            }
         }
      });
      DispenserBlock.registerDispenseBehavior(Items.SHEARS.asItem(), new OptionalDispenseBehavior() {
         protected ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
            World world = p_82487_1_.getWorld();
            if (!world.isRemote()) {
               this.successful = false;
               BlockPos blockpos = p_82487_1_.getBlockPos().offset((Direction)p_82487_1_.getBlockState().get(DispenserBlock.FACING));
               Iterator var5 = world.getEntitiesInAABBexcluding((Entity)null, new AxisAlignedBB(blockpos), (p_lambda$dispenseStack$0_0_) -> {
                  return !p_lambda$dispenseStack$0_0_.isSpectator() && p_lambda$dispenseStack$0_0_ instanceof IShearable;
               }).iterator();

               while(var5.hasNext()) {
                  Entity entity = (Entity)var5.next();
                  IShearable target = (IShearable)entity;
                  if (target.isShearable(p_82487_2_, world, blockpos)) {
                     List<ItemStack> drops = target.onSheared(p_82487_2_, entity.world, blockpos, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, p_82487_2_));
                     Random rand = new Random();
                     drops.forEach((p_lambda$dispenseStack$1_2_) -> {
                        ItemEntity ent = entity.entityDropItem(p_lambda$dispenseStack$1_2_, 1.0F);
                        ent.setMotion(ent.getMotion().add((double)((rand.nextFloat() - rand.nextFloat()) * 0.1F), (double)(rand.nextFloat() * 0.05F), (double)((rand.nextFloat() - rand.nextFloat()) * 0.1F)));
                     });
                     if (p_82487_2_.attemptDamageItem(1, world.rand, (ServerPlayerEntity)null)) {
                        p_82487_2_.setCount(0);
                     }

                     this.successful = true;
                     break;
                  }
               }

               if (!this.successful) {
                  BlockState blockstate = world.getBlockState(blockpos);
                  if (blockstate.isIn(BlockTags.field_226151_aa_)) {
                     int i = (Integer)blockstate.get(BeehiveBlock.field_226873_c_);
                     if (i >= 5) {
                        if (p_82487_2_.attemptDamageItem(1, world.rand, (ServerPlayerEntity)null)) {
                           p_82487_2_.setCount(0);
                        }

                        BeehiveBlock.func_226878_a_(world, blockpos);
                        ((BeehiveBlock)blockstate.getBlock()).func_226877_a_(world, blockstate, blockpos, (PlayerEntity)null, BeehiveTileEntity.State.BEE_RELEASED);
                        this.successful = true;
                     }
                  }
               }
            }

            return p_82487_2_;
         }
      });
   }
}
